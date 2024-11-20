package andrew.samardak.spring_aop.dto.request;

import lombok.Data;

@Data
public class ClientRequestDto {

    private String firstName;

    private String lastName;

    private String middleName;

    private Boolean blockedFor;

    private String blockedWhom;
}
