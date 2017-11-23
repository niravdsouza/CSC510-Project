
module.exports = {
  startStandupButtons: startStandupButtons = {
    attachments: [
        {
            color: '#5A352D',
            pretext: 'We are starting with the standup.\n',
            title: "Select one option.",
            callback_id: 'standupOptions',
            attachment_type: 'default',
            actions: [
                {
                    name: 'Start',
                    text: 'Start',
                    type: 'button',
                    value: 'start',
                },
                {
                    name: 'Snooze',
                    text: 'Snooze',
                    type: 'button',
                    value: 'snooze',
                },
                {
                    name: 'Ignore',
                    text: 'Ignore',
                    type: 'button',
                    value: 'ignore',
                },
            ],
        }
      ]
    },
}
