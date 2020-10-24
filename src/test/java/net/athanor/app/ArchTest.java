package net.athanor.app;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("net.athanor.app");

        noClasses()
            .that()
            .resideInAnyPackage("net.athanor.app.service..")
            .or()
            .resideInAnyPackage("net.athanor.app.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..net.athanor.app.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
