package com.jobeligibility.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Domain interface representing a job requirement that can be evaluated against a set of possessions.
 * This follows the Strategy pattern to allow for different types of requirements.
 * 
 * @author Fopa Kuete Duclair
 * @version 1.0
 */
public interface Requirement {
    
    /**
     * Evaluates whether the given possessions satisfy this requirement.
     * 
     * @param possessions the set of items the candidate possesses
     * @return true if the requirement is satisfied, false otherwise
     */
    boolean isSatisfiedBy(Set<String> possessions);
    
    /**
     * Returns a human-readable description of this requirement.
     * 
     * @return the requirement description
     */
    String getDescription();
} 