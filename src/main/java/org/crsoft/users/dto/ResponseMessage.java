package org.crsoft.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jyepez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    private String message;


}
