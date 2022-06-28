package co.za.carolsBoutique.boutique.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    //added phoneNumber, database changed(laurence)
    private String rating;
    private String comment;
    private String contactInfo;
    private String boutique;
}
