package com.example.commonmodule.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleAPI {
    @JsonProperty("destination_addresses")
    private List<String> destinationAddresses;
    @JsonProperty("origin_addresses")
    private List<String> originAddresses;
    @JsonProperty("rows")
    private List<Rows> rows;
    @JsonProperty("status")
    private String status;
}
