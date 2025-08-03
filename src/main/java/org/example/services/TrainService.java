package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {

    List<Train> trainList;

    public static final String TRAIN_PATH = "src/main/java/org/example/localDb/trains.json";

    ObjectMapper objectMapper = new ObjectMapper();

    public TrainService() throws IOException {
        trainList = objectMapper.readValue(new File(TRAIN_PATH), new TypeReference<List<Train>>() {
        });
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream().filter(train -> validTrain(train , source, destination)).collect(Collectors.toList());
    }

    private boolean validTrain(Train train, String source, String destination) {

        List<String> stationOrder = train.getStations();
        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;

    }

    public void addTrain(Train train) {
        
    }
}
