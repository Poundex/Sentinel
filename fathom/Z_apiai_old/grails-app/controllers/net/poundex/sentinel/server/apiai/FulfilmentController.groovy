package net.poundex.sentinel.server.apiai

import grails.converters.JSON
import org.grails.web.json.JSONObject

import java.time.LocalDate

class FulfilmentController {
	static namespace = "apiai"

	def index(FulfilmentRequest fulfilmentRequest) {
		if(fulfilmentRequest.result.action == "date.get")
			render new FulfilmentResponse("The date is ${LocalDate.now()}", LocalDate.now().toString()) as JSON
		else
			render new FulfilmentResponse("I Don't know", "Don't know") as JSON
	}
}


// emissary => query
// query => apiai
// apiai => fulfilment
// fulfilment => apiai
// apiai => query
// query => emissary
