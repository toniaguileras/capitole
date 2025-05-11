package toni.aguilera.capitole.infraestructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import toni.aguilera.capitole.infraestructure.IntegrationTestBase;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//

@SpringBootTest
public class GetProductsFeature extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_get_for_correct_parameters() throws Exception {
        var category = "ELECTRONICS";
        var orderBy = "SKU";

        mockMvc.perform(get("/v1/products")
                        .param("category", category)
                        .param("orderBy", orderBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(jsonFrom("response_1.json")));

    }

    @Test
    void should_return_get_for_empty_category_with_orderBy() throws Exception {
        var orderBy = "PRICE";

        mockMvc.perform(get("/v1/products")
                        .param("orderBy", orderBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(jsonFrom("response_2.json")));

    }

    @Test
    void should_return_invalid_request_for_invalid_category() throws Exception {
        var category = "VEGETABLES";
        var orderBy = "PRICE";

        mockMvc.perform(get("/v1/products")
                        .param("category", category)
                        .param("orderBy", orderBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    private String jsonFrom(String fileName) throws Exception {
        ClassPathResource resource = new ClassPathResource(fileName);
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }

}
