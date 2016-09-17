var request = require('request');
var cheerio = require('cheerio');

if(process.argv.length > 2){
    process.argv.forEach(function(val){
        if(val != 'node' && val.indexOf('.') < 0){
            getClassesForDept(val);
        }
    });
}else{
    getAllDepartments();
}

function getAllDepartments(){
    var baseUrl = 'http://www.acs.utah.edu/uofu/stu/scheduling/?cmd=index&classtype=g&term=1158';
    request(baseUrl, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            $ = cheerio.load(body);
            $('td').each(function(){
                var abbr = $(this).text().trim();
                if(abbr.length > 1 && abbr.length < 6 && abbr != "top" && abbr == abbr.toUpperCase()){
                    getClassesForDept(abbr);
                }
            });
        }
    });
}
function createCourse(children){

  var flag = $(children).eq(0).text().trim();
  var flagMessage = getFlagMessage(flag);
  var classNum = $(children).eq(1).text().trim();
  var subject = $(children).eq(2).text().trim();
  var catalog = $(children).eq(3).text().trim();
  var section = $(children).eq(4).text().trim();
  var component = $(children).eq(5).text().trim();
  var units = $(children).eq(6).text().trim();
  var title = $(children).eq(7).text().trim();
  var daysTaught = $(children).eq(8).text().trim();
  var time = $(children).eq(9).text().trim();
  var location = $(children).eq(11).text().trim();
  var attr = $(children).eq(12).text().trim();
  var instructor = $(children).eq(13).text().trim();
  var feedback = $(children).eq(14).text().trim();
  var preReq = $(children).eq(15).text().trim();
  var fees = $(children).eq(16).text().trim();

  return course = {
    flagMessage : flagMessage,
    classNum: parseInt(classNum),
    subject: subject,
    catalog: parseInt(catalog),
    section: parseInt(section),
    component: component,
    units: parseFloat(units),
    title: title,
    daysTaught: daysTaught,
    time: time,
    location: location,
    attributes: attr,
    instructor: instructor,
    feedback: feedback,
    preReq: preReq,
    fees: fees
  };

  function getFlagMessage(flag){
    var flagMessage = [];
    if(flag.indexOf("-") > -1){
      flagMessage.push('Class has been deleted.')
    }
    if(flag.indexOf("+") > -1){
      flagMessage.push('Class has been added since March 02, 2015.')
    }
    if(flag.indexOf("@") > -1){
      flagMessage.push('One or more of the following fields have changed: days taught, time taught, credit hours, location, or instructor.');
    }
    if(flag.indexOf("*") > -1){
      flagMessage.push('Please see academic department for class number.')
    }
    return flagMessage;
  }
}

function getClassesForDept(dept){

    var url = 'http://www.acs.utah.edu/uofu/stu/scheduling?term=1158&dept=' + dept + '&classtype=g';
    request(url, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            $ = cheerio.load(body);
            var courses = [];
            $('tr[valign="top"]').each(function(){

                var children = $(this).children();
                var course = createCourse(children);
                if(course.title.length > 0){
                    courses.push(course);
                }
            });
            for(var k = 0; k < courses.length; ++k){
                var course = courses[k];
                console.log(course);
            }
            console.log(courses.length + ' ' + dept + ' classes at the U');
        }
    });
}