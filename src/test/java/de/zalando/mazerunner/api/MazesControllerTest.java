package de.zalando.mazerunner.api;

import de.zalando.mazerunner.Main;
import de.zalando.mazerunner.domain.Maze;
import de.zalando.mazerunner.domain.Mazes;
import de.zalando.mazerunner.service.MazeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class MazesControllerTest {

    private MockMvc mockMvc;

    private Maze maze;

    @Mock
    private MazeService mazeServiceMock;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MazesController(mazeServiceMock)).build();

        maze = new Maze();
        maze.setWidth(3);
        maze.setHeight(3);
        maze.setCode("123");
        maze.setFields("#@##.##..");
    }

    @Test
    public void shouldHideMazeFields() throws Exception {
        when(mazeServiceMock.findAll()).thenReturn(new Mazes(maze));

        mockMvc.perform(get("/mazes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mazes", hasSize(1)))
                .andExpect(jsonPath("$.mazes[0]", not(hasKey("fields"))));

        verify(mazeServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(mazeServiceMock);
    }

    @Test
    public void shouldHideMazeStartPosition() throws Exception {
        when(mazeServiceMock.findAll()).thenReturn(new Mazes(maze));

        mockMvc.perform(get("/mazes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mazes", hasSize(1)))
                .andExpect(jsonPath("$.mazes[0]", not(hasKey("start"))));

        verify(mazeServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(mazeServiceMock);
    }

    @Test
    public void shouldProvideValuesForBasicMazeProperties() throws Exception {
        when(mazeServiceMock.findAll()).thenReturn(new Mazes(maze));

        mockMvc.perform(get("/mazes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mazes", hasSize(1)))
                .andExpect(jsonPath("$.mazes[0].code", is("123")))
                .andExpect(jsonPath("$.mazes[0].width", is(3)))
                .andExpect(jsonPath("$.mazes[0].height", is(3)));

        verify(mazeServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(mazeServiceMock);
    }

    @Test
    public void shouldReturnStartingPositionOfGivenMaze() throws Exception {
        when(mazeServiceMock.get(anyString())).thenReturn(Optional.of(maze));

        mockMvc.perform(get("/mazes/CODE/position/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.x", is(1)))
                .andExpect(jsonPath("$.y", is(0)));

        verify(mazeServiceMock, times(2)).get(anyString());
        verifyNoMoreInteractions(mazeServiceMock);
    }

    @Test
    public void shouldReturn404IfMazeNotFound() throws Exception {
        when(mazeServiceMock.get(anyString())).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/mazes/UNKNOWN/position/start"))
                .andExpect(status().isNotFound());

        verify(mazeServiceMock, times(1)).get(anyString());
        verifyNoMoreInteractions(mazeServiceMock);
    }
}
