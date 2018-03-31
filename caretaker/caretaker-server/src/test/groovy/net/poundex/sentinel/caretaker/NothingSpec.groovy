package net.poundex.sentinel.caretaker

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class NothingSpec extends Specification implements DomainUnitTest<Nothing> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
