package simpleCRUD.unitTest.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MetaDataDto {
//             "1. Information": "Daily Prices (open, high, low, close) and Volumes",
//             "2. Symbol": "IBM",
//             "3. Last Refreshed": "2024-07-17",
//             "4. Output Size": "Compact",
//             "5. Time Zone": "US/Eastern"
    @JsonProperty("1. Information")
    private String information;
    @JsonProperty("2. Symbol")
    private String symbol;
    @JsonProperty("3. Last Refreshed")
    private String lastRefreshed;
    @JsonProperty("4. Output Size")
    private String outputSize;
    @JsonProperty("5. Time Zone")
    private String timeZone;

}
