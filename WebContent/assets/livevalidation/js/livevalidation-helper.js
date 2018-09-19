function addTextPatternValidations(selectors,minimum,maximum)
{
	selectors.forEach(
			function(selector)
			{
				 new LiveValidation(selector).add( Validate.Presence ).add(Validate.Format, { pattern: /^[a-z_ .-]*$/i }).add(Validate.Length, { minimum: minimum, maximum: maximum });
			     	
			});
	
}


function addTextNumberPatternValidations(selectors,minimum,maximum)
{
	selectors.forEach(
			function(selector)
			{
				 new LiveValidation(selector).add( Validate.Presence ).add(Validate.Format, { pattern: /^[a-z0-9_ .-]*$/i }).add(Validate.Length, { minimum: minimum, maximum: maximum });
			     	
			});
	
}



function addTextValidations(selectors,minimum,maximum)
{
	selectors.forEach(
			function(selector)
			{
				 new LiveValidation(selector).add( Validate.Presence ).add(Validate.Length, { minimum: minimum, maximum: maximum });
			     	
			});
	
}
function addNumberPatternValidations(selectors, minimum, maximum) {
	selectors.forEach(function(selector) {
		new LiveValidation(selector).add(Validate.Presence).add(
				Validate.Format, {
					pattern : /^[0-9.%_ .-]*$/i
				}).add(Validate.Length, {
			minimum : minimum,
			maximum : maximum
		});

	});

}


function addNumberValidations(selectors,minimum,maximum)
{
	selectors.forEach(
			function(selector)
			{
				 new LiveValidation(selector).add( Validate.Presence ).add( Validate.Numericality).add(Validate.Length, { minimum: minimum, maximum: maximum });
			     	
			});
	
}
function addEmailValidations(selectors,minimum,maximum)
{
	selectors.forEach(
			function(selector)
			{
				 new LiveValidation(selector).add( Validate.Presence ).add(Validate.Format, { pattern: /^([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})$/i }).add(Validate.Length, { minimum: minimum, maximum: maximum });
			     	
			});
	
}

function addEmptyValidations(selectors)
{
	selectors.forEach(
			function(selector)
			{
				 new LiveValidation(selector).add( Validate.Presence );
			     	
			});
	
}


function addDescriptionValidations(selectors,minimum,maximum)
{
	selectors.forEach(
			function(selector)
			{
				 new LiveValidation(selector).add( Validate.Presence ).add(Validate.Format, { pattern: /^[a-z!@#$%^&*(){}:;+?><=_ .-]*$/i}).add(Validate.Length, { minimum: minimum, maximum: maximum });
			     	
			});
	
}


function addConfirmpasswordValidations(confirmpassword,password)
{
	
new LiveValidation(confirmpassword).add( Validate.Presence ).add( Validate.Confirmation, { match: password } );

}




function addDatePatternValidations(selectors)
{
	selectors.forEach(function(selector)
			{
				 new LiveValidation(selector).add( Validate.Presence ).add(Validate.Format,{ pattern:/^[0-9._-]*$/i});
			     	
				 
				 
				 
			});


}


function addListboxValidations(selectors)
{
selectors.forEach(function(selector)
		
{
 new LiveValidation(selector).add(Validate.Exclusion,{within:['Select'],failureMessage:"Please select Something",caseSensitive: false});	

});

}

function addCheckboxValidations(selectors)
{
selectors.forEach(function(selector)
		
{
 new LiveValidation(selector).add(Validate.Presence).add( Validate.Acceptance );	

});

}



function addFileuploadValidations(selectors)
{
selectors.forEach(function(selector)
		
{
new LiveValidation(selector).add(Validate.Presence,{ failureMessage:"Please Upload file"});

});

}










