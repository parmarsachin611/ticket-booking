package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private static final String USER_PATH = "src/main/java/org/example/localDb/users.json";

    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User user) throws IOException {

        this.user = user;
        loadUsers();

    }

    public UserBookingService() throws IOException {

        loadUsers();

    }

    public static List<Train> getTrains(String source, String destination) {
        TrainService trainService = new TrainService();
        return trainService.searchTrains(source,destination);
    }

    public void loadUsers() throws IOException{

        userList = objectMapper.readValue(new File(USER_PATH), new TypeReference<List<User>>() {});

    }

    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName())  && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();

        return foundUser.isPresent();
    }

    public Boolean signUp(User user1) {
        try{
            System.out.println(user1);
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    public void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBookings() {
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return  user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if(userFetched.isPresent()) {
            userFetched.get().printTicket();
        }
    }

}
