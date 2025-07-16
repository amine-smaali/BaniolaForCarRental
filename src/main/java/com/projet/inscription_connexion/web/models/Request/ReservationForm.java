package  com.projet.inscription_connexion.web.models.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationForm {
    private Long id_client;
    private Long id_car;
    
    @NotNull(message = "date de debut is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate date_deb;

    @NotNull(message = "date de fin is required")
    @Future(message = "End date must be in the future")
    private LocalDate date_fin;

    @AssertTrue(message = "End date must be after start date")
    public boolean isDateRangeValid() {
        return date_fin == null || date_deb == null || date_fin.isAfter(date_deb);
    }

    

    

}
