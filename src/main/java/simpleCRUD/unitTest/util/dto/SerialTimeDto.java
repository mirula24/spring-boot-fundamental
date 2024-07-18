package simpleCRUD.unitTest.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SerialTimeDto {
//            "1. open": "185.4400",
//            "2. high": "187.9400",
//            "3. low": "185.0700",
//            "4. close": "187.4500",
//            "5. volume": "4225302"
    @JsonProperty("1. open")
    private String open;
    @JsonProperty("2. high")
    private String high;
    @JsonProperty("3. low")
    private String low;
        @JsonProperty("4. close")
    private String close;
        @JsonProperty("5. volume")
    private String volume;


}
