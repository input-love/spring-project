package andrew.samardak.spring_aop.dto.response;

import lombok.Data;

@Data
public class ClientResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private Boolean blockedFor;

    private String blockedWhom;
}
