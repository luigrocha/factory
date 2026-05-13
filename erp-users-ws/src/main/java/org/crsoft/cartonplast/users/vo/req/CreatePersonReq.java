package org.crsoft.cartonplast.users.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String ci;

    @NotEmpty
    private String firstLastName;
    private String secondLastName;

    @NotEmpty
    private String firstName;
    private String secondName;
    private LocalDate birthDate;
    private String mobil1;
    private String mobil2;
    private String email1;
    private String email2;
    private String address;
    private String landline;
    private LocalDate contractDate;
    private String observation;

    @NotNull
    private Integer enforce;

    @SuppressWarnings("java:S1948")
    private MultipartFile image;

    private String ethnic;

    @NotEmpty
    private String gender;

    @NotEmpty
    private String group;
}
