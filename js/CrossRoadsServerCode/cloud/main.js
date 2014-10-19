var twilio = require("twilio");
twilio.initialize("AC275d394d4f0465c8e0b6694216f29de2","e131287dc193f0f102d3323cc46f4f2e");


// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});


//curl -X POST -H "X-Parse-Application-Id: ZwqdQKWXjs4vs9n22rqL0gQA0mBoFCooSMtA7BBG" -H "X-Parse-REST-API-Key: wo2DAtaAaQ2CdzTWuM07oChzxmOSb8kMwi2wHRG7" -H "Content-Type: application/json"  -d "{\"objectId\":\"oixOvVCqPD\", \"confirmationCode\":\"111\"}" https://api.parse.com/1/functions/confirmPhone
Parse.Cloud.define("sendConfirmation", function(request, response) {
	var user = request.params.objectId; 
	var UserTable = Parse.Object.extend("User2");	
	var query = new Parse.Query(UserTable);

	query.get(user,{
	    success: function(result) {
		    var phone = result.get("mobile");
		    var confirmationCode = result.get("confirmationCode");

		    twilio.sendSMS({
				From: "+19782527433",
			    To: phone,
			    Body: confirmationCode + " is your croossroads confirmation code"
			  }, {
			    success: function(httpResponse) {response.success("SMS sent!"); },
			    error: function(httpResponse) { response.error("Uh oh, something went wrong"); }
			});
		},
		error: function () {
	      response.error("user lookup failed");
		}
	});
});

//curl -X POST -H "X-Parse-Application-Id: ZwqdQKWXjs4vs9n22rqL0gQA0mBoFCooSMtA7BBG" -H "X-Parse-REST-API-Key: wo2DAtaAaQ2CdzTWuM07oChzxmOSb8kMwi2wHRG7" -H "Content-Type: application/json"  -d "{\"objectId\":\"oixOvVCqPD\", \"confirmationCode\":\"1111\"}" https://api.parse.com/1/functions/authenticateConfirmation
Parse.Cloud.define("authenticateConfirmation", function(request, response) {
	var user = request.params.objectId; 
	var code = request.params.confirmationCode;

	var UserTable = Parse.Object.extend("User2");	
	var query = new Parse.Query(UserTable);

	query.get(user,{
	    success: function(result) {

		    var confirmationCode = result.get("confirmationCode");

		    if (confirmationCode == code) {
				result.set("hasConfirmed", true);
				result.save();
				response.success("correct code");
		    } else {
		    	response.error("wrong code");
		    }
		},
		error: function () {
	      response.error("user lookup failed");
		}
	});
});
















