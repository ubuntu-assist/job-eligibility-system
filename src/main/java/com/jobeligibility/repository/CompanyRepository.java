package com.jobeligibility.repository;

import com.jobeligibility.domain.Company;
import com.jobeligibility.domain.CompositeRequirement;
import com.jobeligibility.domain.SimpleRequirement;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing company data.
 * This follows the Repository pattern to abstract data access.
 * 
 * @author Fopa Kuete Duclair
 * @version 1.0
 */
public class CompanyRepository {
    
    /**
     * Creates the sample companies as specified in the problem statement.
     * In a real application, this would typically load from a database or external service.
     * 
     * @return a list of companies with their requirements
     */
    public List<Company> createSampleCompanies() {
        List<Company> companies = new ArrayList<>();
        
        // Company A: requires an apartment or house, and property insurance
        companies.add(new Company("Company A", 
            new CompositeRequirement(CompositeRequirement.LogicalOperator.AND,
                new CompositeRequirement(CompositeRequirement.LogicalOperator.OR,
                    new SimpleRequirement("apartment"),
                    new SimpleRequirement("house")
                ),
                new SimpleRequirement("property insurance")
            )));
        
        // Company B: requires 5 door car or 4 door car, and a driver's license and car insurance
        companies.add(new Company("Company B",
            new CompositeRequirement(CompositeRequirement.LogicalOperator.AND,
                new CompositeRequirement(CompositeRequirement.LogicalOperator.OR,
                    new SimpleRequirement("5 door car"),
                    new SimpleRequirement("4 door car")
                ),
                new SimpleRequirement("driving license"),
                new SimpleRequirement("car insurance")
            )));
        
        // Company C: requires a social security number and a work permit
        companies.add(new Company("Company C",
            new CompositeRequirement(CompositeRequirement.LogicalOperator.AND,
                new SimpleRequirement("social security number"),
                new SimpleRequirement("work permit")
            )));
        
        // Company D: requires an apartment or a flat or a house
        companies.add(new Company("Company D",
            new CompositeRequirement(CompositeRequirement.LogicalOperator.OR,
                new SimpleRequirement("apartment"),
                new SimpleRequirement("flat"),
                new SimpleRequirement("house")
            )));
        
        // Company E: requires a driver's license and a 2 door car or a 3 door car or a 4 door car or a 5 door car
        companies.add(new Company("Company E",
            new CompositeRequirement(CompositeRequirement.LogicalOperator.AND,
                new SimpleRequirement("driving license"),
                new CompositeRequirement(CompositeRequirement.LogicalOperator.OR,
                    new SimpleRequirement("2 door car"),
                    new SimpleRequirement("3 door car"),
                    new SimpleRequirement("4 door car"),
                    new SimpleRequirement("5 door car")
                )
            )));
        
        // Company F: requires a scooter or a bike, or a motorcycle and a driver's license and motorcycle insurance
        companies.add(new Company("Company F",
            new CompositeRequirement(CompositeRequirement.LogicalOperator.AND,
                new CompositeRequirement(CompositeRequirement.LogicalOperator.OR,
                    new SimpleRequirement("scooter"),
                    new SimpleRequirement("bike"),
                    new SimpleRequirement("motorcycle")
                ),
                new SimpleRequirement("driving license"),
                new SimpleRequirement("motorcycle insurance")
            )));
        
        // Company G: requires a massage qualification certificate and a liability insurance
        companies.add(new Company("Company G",
            new CompositeRequirement(CompositeRequirement.LogicalOperator.AND,
                new SimpleRequirement("massage qualification certificate"),
                new SimpleRequirement("liability insurance")
            )));
        
        // Company H: requires a storage place or a garage
        companies.add(new Company("Company H",
            new CompositeRequirement(CompositeRequirement.LogicalOperator.OR,
                new SimpleRequirement("storage place"),
                new SimpleRequirement("garage")
            )));
        
        // Company J: doesn't require anything
        companies.add(new Company("Company J", null));
        
        // Company K: requires a PayPal account
        companies.add(new Company("Company K",
            new SimpleRequirement("PayPal account")));
        
        return companies;
    }
    
    /**
     * Creates a list of companies with custom requirements.
     * This method can be used for testing or adding new companies.
     * 
     * @param companyData list of company data (name and requirement description)
     * @return a list of companies
     */
    public List<Company> createCompaniesFromData(List<CompanyData> companyData) {
        List<Company> companies = new ArrayList<>();
        
        for (CompanyData data : companyData) {
            companies.add(new Company(data.getName(), data.getRequirement()));
        }
        
        return companies;
    }
    
    /**
     * Data class for creating companies with custom requirements.
     */
    public static class CompanyData {
        private final String name;
        private final com.jobeligibility.domain.Requirement requirement;
        
        public CompanyData(String name, com.jobeligibility.domain.Requirement requirement) {
            this.name = name;
            this.requirement = requirement;
        }
        
        public String getName() {
            return name;
        }
        
        public com.jobeligibility.domain.Requirement getRequirement() {
            return requirement;
        }
    }
} 