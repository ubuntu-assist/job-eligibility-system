package com.jobeligibility.service;

import com.jobeligibility.domain.Company;
import com.jobeligibility.domain.Requirement;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service responsible for evaluating job eligibility based on candidate possessions.
 * This service encapsulates the business logic for determining which companies
 * a candidate can work for.
 * 
 * @author Fopa Kuete Duclair
 * @version 1.0
 */
public class JobEligibilityService {
    
    /**
     * Evaluates job eligibility for all companies based on the candidate's possessions.
     * 
     * @param companies the list of companies to evaluate
     * @param possessions the set of items the candidate possesses
     * @return a map of company names to their eligibility status
     * @throws IllegalArgumentException if companies or possessions is null
     */
    public Map<String, Boolean> evaluateEligibility(List<Company> companies, Set<String> possessions) {
        validateInput(companies, possessions);
        
        return companies.stream()
                .collect(Collectors.toMap(
                        Company::getName,
                        company -> company.canWork(possessions)
                ));
    }
    
    /**
     * Gets a list of companies where the candidate can work.
     * 
     * @param companies the list of companies to evaluate
     * @param possessions the set of items the candidate possesses
     * @return a list of companies where the candidate is eligible to work
     * @throws IllegalArgumentException if companies or possessions is null
     */
    public List<Company> getEligibleCompanies(List<Company> companies, Set<String> possessions) {
        validateInput(companies, possessions);
        
        return companies.stream()
                .filter(company -> company.canWork(possessions))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets a list of companies where the candidate cannot work.
     * 
     * @param companies the list of companies to evaluate
     * @param possessions the set of items the candidate possesses
     * @return a list of companies where the candidate is not eligible to work
     * @throws IllegalArgumentException if companies or possessions is null
     */
    public List<Company> getIneligibleCompanies(List<Company> companies, Set<String> possessions) {
        validateInput(companies, possessions);
        
        return companies.stream()
                .filter(company -> !company.canWork(possessions))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets detailed eligibility information including requirement descriptions.
     * 
     * @param companies the list of companies to evaluate
     * @param possessions the set of items the candidate possesses
     * @return a map containing detailed eligibility information
     * @throws IllegalArgumentException if companies or possessions is null
     */
    public Map<String, EligibilityResult> getDetailedEligibility(List<Company> companies, Set<String> possessions) {
        validateInput(companies, possessions);
        
        return companies.stream()
                .collect(Collectors.toMap(
                        Company::getName,
                        company -> new EligibilityResult(
                                company.canWork(possessions),
                                company.getRequirementDescription()
                        )
                ));
    }
    
    private void validateInput(List<Company> companies, Set<String> possessions) {
        if (companies == null) {
            throw new IllegalArgumentException("Companies list cannot be null");
        }
        if (possessions == null) {
            throw new IllegalArgumentException("Possessions set cannot be null");
        }
    }
    
    /**
     * Result class containing detailed eligibility information.
     */
    public static class EligibilityResult {
        private final boolean eligible;
        private final String requirementDescription;
        
        public EligibilityResult(boolean eligible, String requirementDescription) {
            this.eligible = eligible;
            this.requirementDescription = requirementDescription;
        }
        
        public boolean isEligible() {
            return eligible;
        }
        
        public String getRequirementDescription() {
            return requirementDescription;
        }
        
        @Override
        public String toString() {
            return "EligibilityResult{eligible=" + eligible + 
                   ", requirementDescription='" + requirementDescription + "'}";
        }
    }
} 