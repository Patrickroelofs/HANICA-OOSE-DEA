import oose.dea.StarWars;
import oose.dea.oose.dea.dto.JediDTO;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StarWarsTest {


    @Test
    public void helloWorldTest (){
        // Arrange
        StarWars starWars = new StarWars();

        // Act
        String expected = "May the force be with you, Luke!";
        String actual = starWars.helloWorld();

        // Assert
        assertEquals(expected,actual);
    }

    @Test
    public void getJediTest() {
        Response response = StarWars.getJedi(41);
        JediDTO jediDTO = (JediDTO) response.getEntity();

        assertEquals(200, response.getStatus());
        assertEquals("Thijmen", jediDTO.name);
        assertEquals(41, jediDTO.customerId);
    }


}
