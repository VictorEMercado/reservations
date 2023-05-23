package coherent.test.reservations.models.services;

import coherent.test.reservations.models.entity.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Service
public class ReservationsStatefulStateServiceImpl implements ReservationStatefulStateService {

    private static final String FILE_PATH = "backEnd/reservations_data.json";

    public Set<Reservation> loadFromStateFile() throws IOException{
        ObjectMapper objectMapper = initiateObjectMapper();
        String json = "";
        Set<Reservation> reservations = new HashSet<>();

        if(!(new File(FILE_PATH).exists())) {
            createFileIfNotExists();
            return reservations;
        }
        json = Files.readString(Paths.get(FILE_PATH));

        try {
            reservations = objectMapper.readValue(json, new TypeReference<Set<Reservation>>() {});
        }catch(IOException e){
            e.printStackTrace();
        }
        return reservations;
    }

    public void saveToStateFile(Set<Reservation> reservations) throws IOException{
        ObjectMapper objectMapper = initiateObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(FILE_PATH), reservations);
            System.out.println("Reservations saved to JSON file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObjectMapper initiateObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

    private void createFileIfNotExists() throws IOException{
        File myFile = new File(FILE_PATH);
        try{
            myFile.createNewFile();
        }catch(IOException e){
            System.out.println("The Json data file can´t be read or created");
            e.printStackTrace();
        }
    }
}
