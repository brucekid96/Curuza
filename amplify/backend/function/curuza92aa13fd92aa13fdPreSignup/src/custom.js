exports.handler = async (event, context) => {
  console.log("Event: " + JSON.stringify(event, null, 2));
  console.log("Context: " + context);
  event.response.autoConfirmUser = true;
  event.response.autoVerifyPhone = true;
  return event;
};
