package com.jobeligibility.domain;

import java.util.Objects;

/**
 * Represents a company with its job requirements.
 * This is the main domain entity that encapsulates company information and requirements.
 * 
 * @author Fopa Kuete Duclair
 * @version 1.0
 */
public class Company {
    
    private final String name;
    private final Requirement jobRequirement;
    
    /**
     * Creates a new company with the specified name and job requirement.
     * 
     * @param name the company name (must not be null or empty)
     * @param jobRequirement the job requirement (can be null for companies with no requirements)
     * @throws IllegalArgumentException if name is null or empty
     */
    public Company(String name, Requirement jobRequirement) {
        this.name = validateName(name);
        this.jobRequirement = jobRequirement;
    }
    
    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        return name.trim();
    }
    
    /**
     * Determines if a candidate can work at this company based on their possessions.
     * 
     * @param possessions the set of items the candidate possesses
     * @return true if the candidate can work at this company, false otherwise
     * @throws IllegalArgumentException if possessions is null
     */
    public boolean canWork(java.util.Set<String> possessions) {
        if (possessions == null) {
            throw new IllegalArgumentException("Possessions cannot be null");
        }
        
        // If no requirement is specified, anyone can work
        if (jobRequirement == null) {
            return true;
        }
        
        return jobRequirement.isSatisfiedBy(possessions);
    }
    
    /**
     * Gets the company name.
     * 
     * @return the company name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the job requirement for this company.
     * 
     * @return the job requirement, or null if there are no requirements
     */
    public Requirement getJobRequirement() {
        return jobRequirement;
    }
    
    /**
     * Gets a human-readable description of the job requirements.
     * 
     * @return the requirement description, or "No requirements" if there are none
     */
    public String getRequirementDescription() {
        return jobRequirement != null ? jobRequirement.getDescription() : "No requirements";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Company company = (Company) obj;
        return Objects.equals(name, company.name) && Objects.equals(jobRequirement, company.jobRequirement);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, jobRequirement);
    }
    
    @Override
    public String toString() {
        return "Company{name='" + name + "', requirement=" + getRequirementDescription() + "}";
    }
} 