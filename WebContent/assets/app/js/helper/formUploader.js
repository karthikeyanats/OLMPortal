formUploader=
{
   files:null,
   
   prepareUpload:function(event)
   {
       this.files = event.target.files; 
   },
   uploadFiles:function(event)
   {
      event.stopPropagation(); // Stop stuff happening
      event.preventDefault(); // Totally stop stuff happening
      var formFileData=getFormData(this.files);
      var uploadData=formAjax(url,formFileData);
      submitForm(event, data);
    },
   formAjax:function(url,data)
   {
      var data=null;
      $.ajax({
        url: url,
        type: 'POST',
        data: data,
        cache: false,
        dataType: 'json',
        processData: false, // Don't process the files
        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
        success: function(data, textStatus, jqXHR)
        {
        	if(typeof data.error === 'undefined')
        	{
        		// Success so call function to process the form
        	     return data;
        	}
        	else
        	{        		// Handle errors here
        	      console.log('ERRORS: ' + data.error);
                       data=null;               
        	}
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
        	// Handle errors here
        	console.log('ERRORS: ' + textStatus);
		data=null; 
        	// STOP LOADING SPINNER
        }
    });
    return data; 
   },
   getFormData:function(files){
         var formData = new FormData();
	    $.each(files, function(key, value)
	    {
		    formData.append(key, value);
	    });
       return formData;
    }
};  
