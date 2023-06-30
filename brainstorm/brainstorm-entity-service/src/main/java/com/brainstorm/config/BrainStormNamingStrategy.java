package com.brainstorm.config;

import org.atteo.evo.inflector.English;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class BrainStormNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String underscoredTableName = super.toPhysicalTableName(name, context).getText();
        String pluralizedTableName = English.plural(underscoredTableName);
        return Identifier.toIdentifier(pluralizedTableName);
    }
}
