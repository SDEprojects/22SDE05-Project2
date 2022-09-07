package main.java.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class RoomMovement {
    public static String currentRoom;
    public static Room roomSwitcher;


    //    Room room = new Room();
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<HashMap<String, Room>> typeRef  = new TypeReference<HashMap<String, Room>>() {};
//    TypeReference<HashMap<String, HashSet<String>>> typeRef2 = new TypeReference<HashMap<String, HashSet<String>>>() {};
//
//    private static HashMap<String, HashSet<String>> itemMap;
    static HashMap<String, Room> allRooms;

    {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream resources = classLoader.getResourceAsStream("main/resources/rooms.json");
            allRooms = new ObjectMapper().readValue(resources, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method generates a random room that will be used as the FIRST room
     * the user starts in.
     */
    public void firstRoom(){
        do {
            currentRoom = allRooms.keySet().toArray()[(int) (Math.random() * allRooms.size())] + "";
        } while (currentRoom.equalsIgnoreCase("Throne Room"));
        Room room = allRooms.get(currentRoom);
        roomSwitcher = room;
        textStream("You wake up in a daze...\n",110);
        sleep(1000);
        System.out.println("You look around to collect your bearings...Nothing seems quite real.\n");
        sleep(1000);
        System.out.println("You suddenly remember your mission, you were sent back in time to collect the blood of the first werewolf and return home!\n");
        sleep(2350);
        System.out.println("You are in the " + room.getName()+ ".");
        sleep(750);
        System.out.println(room.getDescription() + "\n");
        sleep(550);

    }


    public static void switchRooms(String location) throws IOException {
        currentRoom = roomSwitcher.getConnectedRooms().get(location);
        Room room = allRooms.get(currentRoom);
        roomSwitcher = room;
    }



    public static HashMap<String, Room> getAllRooms(){
        return allRooms;
    }


    public static void sleep(int timer) {
        try {
            Thread.sleep(timer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private String textStream(String text, int speed) {
        for (int i = 0; i < text.length(); i++) {
            System.out.printf("%c", text.charAt(i));
            sleep(speed);
        }
        return text;
    }
}
