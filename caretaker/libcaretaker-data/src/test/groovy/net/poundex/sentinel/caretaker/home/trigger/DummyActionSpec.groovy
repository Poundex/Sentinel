package net.poundex.sentinel.caretaker.home.trigger

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class DummyActionSpec extends Specification implements DomainUnitTest<DummyAction> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
