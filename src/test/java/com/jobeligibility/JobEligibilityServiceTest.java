package com.jobeligibility;

import com.jobeligibility.domain.Company;
import com.jobeligibility.domain.CompositeRequirement;
import com.jobeligibility.domain.SimpleRequirement;
import com.jobeligibility.service.JobEligibilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the JobEligibilityService class.
 * These tests demonstrate comprehensive testing of the business logic.
 * 
 * @author Fopa Kuete Duclair
 * @version 1.0
 */
@DisplayName("Job Eligibility Service Tests")
class JobEligibilityServiceTest {
    
    private JobEligibilityService service;
    private List<Company> testCompanies;
    
    @BeforeEach
    void setUp() {
        service = new JobEligibilityService();
        testCompanies = createTestCompanies();
    }
    
    @Test
    @DisplayName("Should evaluate eligibility correctly for all companies")
    void testEvaluateEligibility() {
        // Given
        Set<String> possessions = new HashSet<>(Arrays.asList("bike", "driving license"));
        
        // When
        Map<String, Boolean> results = service.evaluateEligibility(testCompanies, possessions);
        
        // Then
        assertNotNull(results);
        assertEquals(4, results.size());
        assertTrue(results.get("Company J")); // No requirements
        assertFalse(results.get("Company F")); // Missing motorcycle insurance
        assertFalse(results.get("Company A")); // Missing property insurance
        assertFalse(results.get("Company K")); // Missing PayPal account
    }
    
    @Test
    @DisplayName("Should return correct list of eligible companies")
    void testGetEligibleCompanies() {
        // Given
        Set<String> possessions = new HashSet<>(Arrays.asList("bike", "driving license"));
        
        // When
        List<Company> eligibleCompanies = service.getEligibleCompanies(testCompanies, possessions);
        
        // Then
        assertEquals(1, eligibleCompanies.size());
        assertTrue(eligibleCompanies.stream().anyMatch(c -> c.getName().equals("Company J")));
    }
    
    @Test
    @DisplayName("Should return correct list of ineligible companies")
    void testGetIneligibleCompanies() {
        // Given
        Set<String> possessions = new HashSet<>(Arrays.asList("bike", "driving license"));
        
        // When
        List<Company> ineligibleCompanies = service.getIneligibleCompanies(testCompanies, possessions);
        
        // Then
        assertEquals(3, ineligibleCompanies.size());
        assertTrue(ineligibleCompanies.stream().anyMatch(c -> c.getName().equals("Company A")));
        assertTrue(ineligibleCompanies.stream().anyMatch(c -> c.getName().equals("Company F")));
        assertTrue(ineligibleCompanies.stream().anyMatch(c -> c.getName().equals("Company K")));
    }
    
    @Test
    @DisplayName("Should return detailed eligibility information")
    void testGetDetailedEligibility() {
        // Given
        Set<String> possessions = new HashSet<>(Arrays.asList("bike", "driving license"));
        
        // When
        Map<String, JobEligibilityService.EligibilityResult> results = 
            service.getDetailedEligibility(testCompanies, possessions);
        
        // Then
        assertNotNull(results);
        assertEquals(4, results.size());
        
        JobEligibilityService.EligibilityResult companyJ = results.get("Company J");
        assertTrue(companyJ.isEligible());
        assertEquals("No requirements", companyJ.getRequirementDescription());
        
        JobEligibilityService.EligibilityResult companyA = results.get("Company A");
        assertFalse(companyA.isEligible());
        assertTrue(companyA.getRequirementDescription().contains("property insurance"));
    }
    
    @Test
    @DisplayName("Should handle empty possessions set")
    void testEmptyPossessions() {
        // Given
        Set<String> possessions = new HashSet<>();
        
        // When
        Map<String, Boolean> results = service.evaluateEligibility(testCompanies, possessions);
        
        // Then
        assertTrue(results.get("Company J")); // No requirements
        assertFalse(results.get("Company A")); // Has requirements
        assertFalse(results.get("Company F")); // Has requirements
        assertFalse(results.get("Company K")); // Has requirements
    }
    
    @Test
    @DisplayName("Should handle case-insensitive matching")
    void testCaseInsensitiveMatching() {
        // Given
        Set<String> possessions = new HashSet<>(Arrays.asList("BIKE", "DRIVING LICENSE", "MOTORCYCLE INSURANCE"));
        
        // When
        Map<String, Boolean> results = service.evaluateEligibility(testCompanies, possessions);
        
        // Then
        assertTrue(results.get("Company F")); // Should match despite case difference
    }
    
    @Test
    @DisplayName("Should throw exception for null companies list")
    void testNullCompaniesList() {
        // Given
        Set<String> possessions = new HashSet<>(Arrays.asList("bike"));
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> 
            service.evaluateEligibility(null, possessions));
    }
    
    @Test
    @DisplayName("Should throw exception for null possessions set")
    void testNullPossessionsSet() {
        // Given
        List<Company> companies = createTestCompanies();
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> 
            service.evaluateEligibility(companies, null));
    }
    
    @Test
    @DisplayName("Should handle complex AND/OR requirements correctly")
    void testComplexRequirements() {
        // Given
        Set<String> possessions = new HashSet<>(Arrays.asList("bike", "driving license", "motorcycle insurance"));
        
        // When
        Map<String, Boolean> results = service.evaluateEligibility(testCompanies, possessions);
        
        // Then
        assertTrue(results.get("Company F")); // Has bike OR scooter OR motorcycle AND driving license AND motorcycle insurance
    }
    
    /**
     * Creates a set of test companies for unit testing.
     */
    private List<Company> createTestCompanies() {
        List<Company> companies = new ArrayList<>();
        
        // Company A: requires apartment OR house AND property insurance
        companies.add(new Company("Company A", 
            new CompositeRequirement(CompositeRequirement.LogicalOperator.AND,
                new CompositeRequirement(CompositeRequirement.LogicalOperator.OR,
                    new SimpleRequirement("apartment"),
                    new SimpleRequirement("house")
                ),
                new SimpleRequirement("property insurance")
            )));
        
        // Company F: requires scooter OR bike OR motorcycle AND driving license AND motorcycle insurance
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
        
        // Company J: no requirements
        companies.add(new Company("Company J", null));
        
        // Company K: requires PayPal account
        companies.add(new Company("Company K",
            new SimpleRequirement("PayPal account")));
        
        return companies;
    }
} 