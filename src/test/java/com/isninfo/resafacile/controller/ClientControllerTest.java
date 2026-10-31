package com.isninfo.resafacile.controller;

import com.isninfo.resafacile.model.Client;
import com.isninfo.resafacile.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Épisode 8 : teste la couche HTTP SANS MySQL.
 * <p>
 * @WebMvcTest ne démarre que la partie web (contrôleurs et GlobalExceptionHandler).
 * Le ClientService est remplacé par une doublure (@MockitoBean) : on décide ce
 * qu'elle répond, et on vérifie ce que le contrôleur en fait.
 * Ces tests passent même si MySQL est éteint.
 */
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Test
    void creerUnClientValideRenvoie201SansLaNoteInterne() throws Exception {
        Client cree = new Client(1L, "Marie Nguema", "marie@example.com", "+240 222 000 001");
        cree.setNoteInterne("Facture impayée : à relancer");
        when(clientService.creerClient(any(Client.class))).thenReturn(cree);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nom":"Marie Nguema","email":"marie@example.com","telephone":"+240 222 000 001"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("Marie Nguema"))
                .andExpect(jsonPath("$.noteInterne").doesNotExist());
    }

    @Test
    void creerSansNomRenvoie400AvecLeDetailDuChamp() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"marie@example.com"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statut").value(400))
                .andExpect(jsonPath("$.details.nom").value("Le nom est obligatoire"));

        verifyNoInteractions(clientService);
    }

    @Test
    void creerAvecUnEmailInvalideRenvoie400() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nom":"Marie Nguema","email":"pas-un-email"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.email").value("L'email n'est pas valide"));
    }

    @Test
    void creerAvecUnTelephoneInvalideRenvoie400() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nom":"Marie Nguema","email":"marie@example.com","telephone":"abc"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.telephone")
                        .value("Le téléphone doit contenir 6 à 20 chiffres, espaces ou +"));
    }

    @Test
    void unJsonCasseRenvoie400() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\": "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Le corps de la requête n'est pas un JSON valide"));
    }

    @Test
    void unClientInexistantRenvoie404AvecUnMessageJson() throws Exception {
        when(clientService.trouverParId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clients/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statut").value(404))
                .andExpect(jsonPath("$.message").value("Client introuvable : 99"))
                .andExpect(jsonPath("$.chemin").value("/api/clients/99"));
    }

    @Test
    void unEmailDejaUtiliseRenvoie409() throws Exception {
        when(clientService.creerClient(any(Client.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nom":"Marie Nguema","email":"marie@example.com"}
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.statut").value(409));
    }
}
