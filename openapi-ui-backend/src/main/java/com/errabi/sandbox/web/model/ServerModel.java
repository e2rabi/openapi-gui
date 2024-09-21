package com.errabi.sandbox.web.model;

import lombok.Builder;
import lombok.With;

import java.util.Set;

@With
@Builder
public record ServerModel(Long id, String url, String description, Set<ServerVariableModel> variables) {

}
