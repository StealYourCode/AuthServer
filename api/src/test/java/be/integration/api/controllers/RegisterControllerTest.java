package be.integration.api.controllers;
//
//import static org.junit.jupiter.api.Assertions.*;
//import be.integration.api.dtos.RegisterRequest;
//import be.integration.models.entities.User;
//import be.integration.repository.IUserRepository;
//import be.integration.services.CertServices;
//import be.integration.services.TokenServices;
//import be.integration.services.UserServices;
//import junit.framework.TestCase;
//
//public class RegisterControllerTest extends TestCase {
//
//    private RegisterController registerController;
//    private IUserRepository userRepository;
//    private UserServices userServices;
//
//    @Override
//    protected void setUp() throws Exception {
//        userRepository = new IUserRepository();
//        CertServices certServices = new CertServices();
//        TokenServices tokenServices = new TokenServices(certServices);
//        userServices = new UserServices(userRepository, tokenServices);
//        registerController = new RegisterController(userServices);
//    }
//
//    public void testSuccessfulRegistration() {
//        RegisterRequest request = new RegisterRequest("testUser", "Str0ngP@ssword!", "test@example.com");
//        var response = registerController.registerUser(request);
//        assertEquals(201, response.getStatusCodeValue());
//    }
//
//    public void testDuplicateUserRegistration() {
//        RegisterRequest request = new RegisterRequest("testUser", "Str0ngP@ssword!", "test@example.com");
//        registerController.registerUser(request); // First time
//        var response = registerController.registerUser(request); // Second time
//        assertEquals(400, response.getStatusCodeValue());
//    }
//}
//