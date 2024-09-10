package com.errabi.sandbox.web.model;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record ServerModel(Long id, String url, String description) {

}
