package bg.autohouse.web.models.view;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileViewModel {

    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
