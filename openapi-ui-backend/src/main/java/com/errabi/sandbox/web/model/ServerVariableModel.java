package com.errabi.sandbox.web.model;

import lombok.Builder;
import lombok.With;


@With
@Builder
public record ServerVariableModel(Long id, String name, String description,
                                  String defaultValue, String value,ServerModel server) {
}
