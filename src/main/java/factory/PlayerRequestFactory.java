package factory;

import dto.PlayerRequestDTO;

import java.util.*;
import java.util.stream.IntStream;

public class PlayerRequestFactory {

    private static PlayerRequestDTO createPlayer(int index) {
        var password = UUID.randomUUID().toString();

        return new PlayerRequestDTO(
                "EUR",
                UUID.randomUUID() + "@example.com",
                "Username" + index,
                password,
                password,
                "Surname" + index,
                "username_" + index);
    }

    public static List<PlayerRequestDTO> createPlayersList(int playersQuantity) {
            return IntStream.range(0, playersQuantity)
                    .mapToObj(PlayerRequestFactory::createPlayer)
                    .toList();
        }
}
