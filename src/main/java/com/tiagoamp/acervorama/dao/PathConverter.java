package com.tiagoamp.acervorama.dao;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.AttributeConverter;

public class PathConverter implements AttributeConverter<Path, String> {
	
	@Override
	public String convertToDatabaseColumn(Path attribute) {
		return attribute != null ? attribute.toString() : null;
	}

	@Override
	public Path convertToEntityAttribute(String dbData) {
		return dbData != null ? Paths.get(dbData) : null;
	}
	
}
