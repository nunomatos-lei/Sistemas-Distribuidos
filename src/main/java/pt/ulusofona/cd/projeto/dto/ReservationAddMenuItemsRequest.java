package pt.ulusofona.cd.projeto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ReservationAddMenuItemsRequest {
    @NotNull
    private List<UUID> menuItemsId;
}
