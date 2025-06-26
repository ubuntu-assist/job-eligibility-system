package com.jobeligibility.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a composite requirement that combines multiple sub-requirements using logical operators.
 * This implements the Composite pattern to allow for complex requirement hierarchies.
 * 
 * @author Fopa Kuete Duclair
 * @version 1.0
 */
public class CompositeRequirement implements Requirement {
    
    /**
     * Defines the logical operator used to combine sub-requirements.
     */
    public enum LogicalOperator {
        AND("AND"),
        OR("OR");
        
        private final String symbol;
        
        LogicalOperator(String symbol) {
            this.symbol = symbol;
        }
        
        public String getSymbol() {
            return symbol;
        }
    }
    
    private final LogicalOperator operator;
    private final List<Requirement> subRequirements;
    
    /**
     * Creates a new composite requirement with the specified operator and sub-requirements.
     * 
     * @param operator the logical operator to use when combining sub-requirements
     * @param subRequirements the sub-requirements to combine (must not be null or empty)
     * @throws IllegalArgumentException if subRequirements is null or empty
     */
    public CompositeRequirement(LogicalOperator operator, Requirement... subRequirements) {
        this(operator, Arrays.asList(subRequirements));
    }
    
    /**
     * Creates a new composite requirement with the specified operator and sub-requirements.
     * 
     * @param operator the logical operator to use when combining sub-requirements
     * @param subRequirements the list of sub-requirements to combine (must not be null or empty)
     * @throws IllegalArgumentException if subRequirements is null or empty
     */
    public CompositeRequirement(LogicalOperator operator, List<Requirement> subRequirements) {
        this.operator = Objects.requireNonNull(operator, "Operator cannot be null");
        this.subRequirements = validateSubRequirements(subRequirements);
    }
    
    private List<Requirement> validateSubRequirements(List<Requirement> requirements) {
        if (requirements == null || requirements.isEmpty()) {
            throw new IllegalArgumentException("Sub-requirements cannot be null or empty");
        }
        if (requirements.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Sub-requirements cannot contain null elements");
        }
        return Collections.unmodifiableList(requirements);
    }
    
    @Override
    public boolean isSatisfiedBy(Set<String> possessions) {
        Objects.requireNonNull(possessions, "Possessions set cannot be null");
        
        return switch (operator) {
            case AND -> subRequirements.stream().allMatch(req -> req.isSatisfiedBy(possessions));
            case OR -> subRequirements.stream().anyMatch(req -> req.isSatisfiedBy(possessions));
        };
    }
    
    @Override
    public String getDescription() {
        StringBuilder description = new StringBuilder("(");
        for (int i = 0; i < subRequirements.size(); i++) {
            if (i > 0) {
                description.append(" ").append(operator.getSymbol()).append(" ");
            }
            description.append(subRequirements.get(i).getDescription());
        }
        description.append(")");
        return description.toString();
    }
    
    public LogicalOperator getOperator() {
        return operator;
    }
    
    public List<Requirement> getSubRequirements() {
        return subRequirements;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CompositeRequirement that = (CompositeRequirement) obj;
        return operator == that.operator && Objects.equals(subRequirements, that.subRequirements);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(operator, subRequirements);
    }
    
    @Override
    public String toString() {
        return "CompositeRequirement{operator=" + operator + ", subRequirements=" + subRequirements + "}";
    }
} 