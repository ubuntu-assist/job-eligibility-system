package com.jobeligibility;

import com.jobeligibility.domain.Company;
import com.jobeligibility.repository.CompanyRepository;
import com.jobeligibility.service.JobEligibilityService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Main application class that demonstrates the job eligibility evaluation system.
 * This class showcases the complete solution to the technical task.
 * 
 * @author Fopa Kuete Duclair
 * @version 1.0
 */
public class JobEligibilityApplication {
    
    public static void main(String[] args) {
        System.out.println("=== Job Eligibility Evaluation System ===\n");
        
        // Initialize components
        CompanyRepository repository = new CompanyRepository();
        JobEligibilityService service = new JobEligibilityService();
        
        // Create sample companies
        List<Company> companies = repository.createSampleCompanies();
        
        // Define candidate possessions (bike and driving license as per problem statement)
        Set<String> candidatePossessions = new HashSet<>(Arrays.asList("bike", "driving license"));
        
        System.out.println("Candidate Possessions: " + candidatePossessions);
        System.out.println("Total Companies: " + companies.size() + "\n");
        
        // Evaluate eligibility
        Map<String, Boolean> eligibilityResults = service.evaluateEligibility(companies, candidatePossessions);
        
        // Display results
        displayResults(eligibilityResults, companies, service, candidatePossessions);
        
        // Display detailed analysis
        displayDetailedAnalysis(companies, service, candidatePossessions);
    }
    
    /**
     * Displays the basic eligibility results.
     */
    private static void displayResults(Map<String, Boolean> results, List<Company> companies, 
                                     JobEligibilityService service, Set<String> possessions) {
        System.out.println("=== ELIGIBILITY RESULTS ===");
        
        int eligibleCount = 0;
        int ineligibleCount = 0;
        
        for (Map.Entry<String, Boolean> entry : results.entrySet()) {
            String status = entry.getValue() ? "✓ CAN WORK" : "✗ CANNOT WORK";
            System.out.printf("%-12s: %s%n", entry.getKey(), status);
            
            if (entry.getValue()) {
                eligibleCount++;
            } else {
                ineligibleCount++;
            }
        }
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Eligible Companies: " + eligibleCount);
        System.out.println("Ineligible Companies: " + ineligibleCount);
        System.out.println("Success Rate: " + String.format("%.1f%%", (eligibleCount * 100.0 / companies.size())));
    }
    
    /**
     * Displays detailed analysis including requirements and reasoning.
     */
    private static void displayDetailedAnalysis(List<Company> companies, JobEligibilityService service, 
                                              Set<String> possessions) {
        System.out.println("\n=== DETAILED ANALYSIS ===");
        
        Map<String, JobEligibilityService.EligibilityResult> detailedResults = 
            service.getDetailedEligibility(companies, possessions);
        
        System.out.println("\n--- ELIGIBLE COMPANIES ---");
        detailedResults.entrySet().stream()
                .filter(entry -> entry.getValue().isEligible())
                .forEach(entry -> {
                    System.out.printf("%-12s: Requirements: %s%n", 
                        entry.getKey(), entry.getValue().getRequirementDescription());
                });
        
        System.out.println("\n--- INELIGIBLE COMPANIES ---");
        detailedResults.entrySet().stream()
                .filter(entry -> !entry.getValue().isEligible())
                .forEach(entry -> {
                    System.out.printf("%-12s: Requirements: %s%n", 
                        entry.getKey(), entry.getValue().getRequirementDescription());
                });
        
        // Show what the candidate is missing
        System.out.println("\n--- MISSING REQUIREMENTS ANALYSIS ---");
        detailedResults.entrySet().stream()
                .filter(entry -> !entry.getValue().isEligible())
                .forEach(entry -> {
                    System.out.printf("%-12s: Missing requirements for: %s%n", 
                        entry.getKey(), entry.getValue().getRequirementDescription());
                });
    }
} 