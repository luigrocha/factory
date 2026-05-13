package org.crsoft.cartonplast.users.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String ci;
    private String firstLastName;
    private String secondLastName;
    private String firstName;
    private String secondName;
    private String fullName;
    private LocalDate birthDate;
    private String mobil1;
    private String mobil2;
    private String email1;
    private String email2;
    private String address;
    private String landline;
    private LocalDate contractDate;
    private String observation;
    private Integer enforce;
    private String imageName;
    private EthnicRes ethnic;
    private GenderRes gender;
    private GroupRes group;
}
