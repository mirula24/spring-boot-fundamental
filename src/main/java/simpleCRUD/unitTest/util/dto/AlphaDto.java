package simpleCRUD.unitTest.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;


@Getter
@Setter
public class AlphaDto {
    @JsonProperty("Meta Data")
    private MetaDataDto metaData;


    @JsonProperty("Time Series (Daily)")
    private Map<String,SerialTimeDto> serialTimeDto;
}
