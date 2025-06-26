package com.jobeligibility.domain;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a simple requirement for a single item.
 * This is a leaf node in the requirement hierarchy.
 * 
 * @author Fopa Kuete Duclair
 * @version 1.0
 */
public class SimpleRequirement implements Requirement {
    
    private final String requiredItem;
    
    /**
     * Creates a new simple requirement for the specified item.
     * 
     * @param requiredItem the item that is required (must not be null or empty)
     * @throws IllegalArgumentException if requiredItem is null or empty
     */
    public SimpleRequirement(String requiredItem) {
        this.requiredItem = validateRequiredItem(requiredItem);
    }
    
    private String validateRequiredItem(String item) {
        if (item == null || item.trim().isEmpty()) {
            throw new IllegalArgumentException("Required item cannot be null or empty");
        }
        return item.trim().toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Set<String> possessions) {
        Objects.requireNonNull(possessions, "Possessions set cannot be null");
        return possessions.stream()
                .map(s -> s.trim().toLowerCase())
                .anyMatch(possession -> possession.equals(requiredItem));
    }
    
    @Override
    public String getDescription() {
        return requiredItem;
    }
    
    public String getRequiredItem() {
        return requiredItem;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SimpleRequirement that = (SimpleRequirement) obj;
        return Objects.equals(requiredItem, that.requiredItem);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(requiredItem);
    }
    
    @Override
    public String toString() {
        return "SimpleRequirement{requiredItem='" + requiredItem + "'}";
    }
} 