var fs = require('fs'); 
var readline = require('readline'); 
var google = require('googleapis'); 
var googleAuth = require('google-auth-library'); 
 
const sheet = {}; 
// If modifying these scopes, delete your previously saved credentials 
// at ~/.credentials/sheets.googleapis.com-nodejs-quickstart.json 
var SCOPES = ['https://www.googleapis.com/auth/spreadsheets']; 
var TOKEN_DIR = (process.env.HOME || process.env.HOMEPATH || 
    process.env.USERPROFILE) + '/.credentials/'; 
var TOKEN_PATH = TOKEN_DIR + 'sheets.googleapis.com-nodejs-quickstart.json'; 

 
//------Synchronous version of readFile----// 
var content = process.env.client_secret;
// Authorize a client with the loaded credentials, then call the 
// Google Sheets API. 
sheet.auth = authorize(JSON.parse(content)); 
 
/** 
 * Create an OAuth2 client with the given credentials, and then execute the 
 * given callback function. 
 * 
 * @param {Object} credentials The authorization client credentials. 
 * @param {function} callback The callback to call with the authorized client. 
 */ 
function authorize(credentials) { 
  var clientSecret = credentials.installed.client_secret; 
  var clientId = credentials.installed.client_id; 
  var redirectUrl = credentials.installed.redirect_uris[0]; 
  var auth = new googleAuth(); 
  var oauth2Client = new auth.OAuth2(clientId, clientSecret, redirectUrl); 
  // Check if we have previously stored a token. 
  var token = fs.readFileSync(TOKEN_PATH); 
  oauth2Client.credentials = JSON.parse(token); 
  return oauth2Client; 
} 
 
/** 
 * Get and store new token after prompting for user authorization, and then 
 * execute the given callback with the authorized OAuth2 client. 
 * 
 * @param {google.auth.OAuth2} oauth2Client The OAuth2 client to get token for. 
 * @param {getEventsCallback} callback The callback to call with the authorized 
 *     client. 
 */ 
function getNewToken(oauth2Client) { 
  var authUrl = oauth2Client.generateAuthUrl({ 
    access_type: 'offline', 
    scope: SCOPES 
  }); 
  console.log('Authorize this app by visiting this url: ', authUrl); 
  var rl = readline.createInterface({ 
    input: process.stdin, 
    output: process.stdout 
  }); 
  rl.question('Enter the code from that page here: ', function(code) { 
    rl.close(); 
    oauth2Client.getToken(code, function(err, token) { 
      if (err) { 
        console.log('Error while trying to retrieve access token', err); 
        return; 
      } 
      oauth2Client.credentials = token; 
      storeToken(token); 
      return token; 
 
    }); 
  }); 
} 
 
/** 
 * Store token to disk be used in later program executions. 
 * 
 * @param {Object} token The token to store to disk. 
 */ 
function storeToken(token) { 
  try { 
    fs.mkdirSync(TOKEN_DIR); 
  } catch (err) { 
    if (err.code != 'EEXIST') { 
      throw err; 
    } 
  } 
  fs.writeFile(TOKEN_PATH, JSON.stringify(token)); 
  console.log('Token stored to ' + TOKEN_PATH); 
} 
 
/**Store the Google Auth Credentials */ 
function storeAuthCreds(authToken) { 
  fs.writeFile('./authCreds.json', JSON.stringify(authToken)); 
  console.log('authToken stored to authCreds.json'); 
} 
 
sheet.retrieveAllAnswers = function(spreadsheet_id,flush,questions_list,callback) { 
  var sheets = google.sheets('v4'); 
  var output = {}; 
  sheets.spreadsheets.values.get({ 
    auth: sheet.auth, 
    spreadsheetId: spreadsheet_id, 
    range: 'Sheet1!A2:Z', 
  }, function(err, response) { 
    if (err) { 
      console.error('Retrieve Answers: The API returned an error: ' + err); 
      return; 
    } 
    //console.log(response.values);
    var rows = response.values; 
    if (rows){
      for (var i = 0; i < rows.length; i++) { 
        var row = rows[i]; 
        // Take each row. Parse first column as answerer name. Parse rest columns as answers based on the questions in questions_list. 
        var user = row[0]; 
        output[user] = {}; 
        if (row.length>1){ 
          for (var j = 0;j<row.length-1;j++){ 
            output[user][questions_list[j]] = row[j+1]; 
          } 
        } 
    }; 
    if (flush){
    //Flush the spreadsheet since the answers have been retrieved 
    sheets.spreadsheets.values.clear({ 
      spreadsheetId: spreadsheet_id, 
      range: 'Sheet1!A2:Z',   
      auth: sheet.auth, 
    }, function(err, response) { 
      if (err) { 
        console.error('Flushing Answers: The API returned an error: ' + err); 
        return; 
      } 
      //Print the response after clearing the sheet 
      console.log(JSON.stringify(response, null, 2)); 
    }); 
  }
}
    callback(output); 
  }); 
} 

sheet.retrieveAllAnswersList = function(spreadsheet_id,flush,callback) { 
  var sheets = google.sheets('v4'); 
  var output = {}; 
  sheets.spreadsheets.values.get({ 
    auth: sheet.auth, 
    spreadsheetId: spreadsheet_id, 
    range: 'Sheet1!A2:Z', 
  }, function(err, response) { 
    if (err) { 
      console.error('Retrieve Answers: The API returned an error: ' + err); 
      return; 
    } 
    var rows = response.values;
    if (rows){
    console.log(response); 
      for (var i = 0; i < rows.length; i++) { 
        var row = rows[i]; 
        // Take each row. Parse first column as answerer name. Parse rest columns as answers based on the questions in questions_list. 
        var user = row[0]; 
        output[user] = row.slice(1); 
    }; 
    if (flush){
    //Flush the spreadsheet since the answers have been retrieved 
    sheets.spreadsheets.values.clear({ 
      spreadsheetId: spreadsheet_id, 
      range: 'Sheet1!A2:Z',   
      auth: sheet.auth, 
    }, function(err, response) { 
      if (err) { 
        console.error('Flushing Answers: The API returned an error: ' + err); 
        return; 
      } 
    }); 
    }
  }
    //Print the response after clearing the sheet 
    //console.log(JSON.stringify(output, null, 2));
    callback(output); 
  }); 
} 
 
sheet.storeAnswers = function(spreadsheet_id,user_id,answers_list,callback) { 
  var sheets = google.sheets('v4'); 
  var value = [user_id,...answers_list]; 
  sheets.spreadsheets.values.append({ 
    auth: sheet.auth, 
    spreadsheetId: spreadsheet_id, 
    range: 'Sheet1!A2:Z', 
    valueInputOption:"USER_ENTERED", 
    resource: { 
    values: [value]}, 
  }, function(err, response) { 
    if (err) { 
      console.error('Store Answers: The API returned an error: ' + err); 
      return; 
    } 
    var updates = response.updates;
    callback(updates); 
 
  }); 
} 

sheet.storeQuestions = function(spreadsheet_id,first_cell,question_list,callback) { 
  var sheets = google.sheets('v4'); 
  var value = [first_cell,...question_list]; 
  sheets.spreadsheets.values.update({ 
    auth: sheet.auth, 
    spreadsheetId: spreadsheet_id, 
    range: 'Sheet1!A1:Z1', 
    valueInputOption:"USER_ENTERED", 
    resource: { 
    values: [value]}, 
  }, function(err, response) { 
    if (err) { 
      console.error('Add Questions: The API returned an error: ' + err); 
      return; 
    } else {
      var updates = response;
    callback(updates); 
    }
  }); 
} 

sheet.modifyQuestions = function(spreadsheet_id,first_cell,question_list,callback) { 
  var sheets = google.sheets('v4'); 
  var value = [first_cell,...question_list]; 
  sheets.spreadsheets.values.clear({
    auth: sheet.auth, 
    spreadsheetId: spreadsheet_id, 
    range: 'Sheet1!A1:Z1',  
  }, function(err, response) { 
    if (err) { 
      console.error('Clear Questions: The API returned an error: ' + err); 
      return; 
    } else {
  sheets.spreadsheets.values.update({ 
    auth: sheet.auth, 
    spreadsheetId: spreadsheet_id, 
    range: 'Sheet1!A1:Z1', 
    valueInputOption:"USER_ENTERED", 
    resource: { 
    values: [value]}, 
  }, function(err, response) { 
    if (err) { 
      console.error('Update Questions: The API returned an error: ' + err); 
      return; 
    } else {
      var updates = response;
    callback(updates); 
    }
  }); }});
} 
sheet.createSheet = function(callback) { 
  var sheets = google.sheets('v4'); 
  sheets.spreadsheets.create({ 
    auth: sheet.auth, 
    resource: {}, 
  }, function(err, response) { 
    if (err) { 
      console.error('Create New Sheet: The API returned an error: ' + err); 
      return; 
    } 
    var spreadsheetId = response.spreadsheetId; 
    callback(spreadsheetId); 
  }); 
} 
 
module.exports = sheet; 
/** 
/--------Examples------------/ 
*/ 
//sheet.retrieveAllAnswers('1AoWv5KqCiEUVE3_fsv7QP8vUZvv7UwzSUnkERrSY9wc',false,['Gender','Class Level','Home State','Major','Extracurricular Activity'],function(response){ 
//  console.log(response);}); 
//sheet.storeAnswers('1AoWv5KqCiEUVE3_fsv7QP8vUZvv7UwzSUnkERrSY9wc','Gender',['Class Level','Home State','Major','Extracurricular Activity'],function(response){ 
//   console.log(response);}); 
/* 
*/ 