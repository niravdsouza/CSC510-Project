const nodemailer = require('nodemailer');

module.exports = {
  // TODO: fetch questions and answers from sheets
  generateReport: function(standupConfig, answers) {
    var standupReport = "Here's the consolidated report for today's standup.\n";
    for (var user in answers) {
      standupReport += `\n${user}'s responses:\n`;
      for(var i = 0; i < standupConfig.questions.length; i++) {
        standupReport += `${standupConfig.questions[i]}\n`;
        standupReport += `${answers[user][i]}\n`;
      }
    }

    return standupReport;
  },


  postReportToChannel: function(bot, report, channelId) {
    bot.say(
    {
      text: report,
      channel: channelId
    });
  },


  emailReport: function(report, emails) {
    var smtpTransport = nodemailer.createTransport("smtps://whatbot.ncsu%40gmail.com:"+encodeURIComponent('12345ABCDE') + "@smtp.gmail.com:465");

    // Send email to each participant
    for (var user in emails) {
      smtpTransport.sendMail({
        from: "whatbot.ncsu@gmail.com",
        to: emails[user],
        subject: "Report",
        text: report
      },
      function(error, response){
        if(error){
          console.log(error);
        }
      });
    }
    smtpTransport.close(); // shut down the connection pool. Comment this line out to continue sending emails.
  }
}
