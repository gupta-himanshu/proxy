package auth

import io.flow.customer.v0.models.Customer
import io.flow.customer.v0.{Client => CustomerClient}
import io.flow.proxy.auth.v0.models.AuthData

import scala.concurrent.{ExecutionContext, Future}

trait CustomerAuthHelper extends LoggingHelper {

  def customerClient: CustomerClient
  def requestHeadersUtil: RequestHeadersUtil

  private[auth] def getCustomerResolvedToken(
    requestId: String,
    customerNumber: String,
    sessionResolvedTokenOption: Option[AuthData]
  )(implicit ec: ExecutionContext): Future[Option[AuthData]] = {
    sessionResolvedTokenOption.map { t =>
      getCustomerResolvedToken(
        requestId = requestId,
        customerNumber = customerNumber,
        sessionResolvedToken = t
      )
    }.getOrElse(Future.successful(None))
  }

  private[this] def getCustomerResolvedToken(
    requestId: String,
    customerNumber: String,
    sessionResolvedToken: AuthData
  )(implicit ec: ExecutionContext): Future[Option[AuthData]] = {
    sessionResolvedToken.organizationId.map { organizationId =>
      getCustomer(
        requestId = requestId,
        organizationId = organizationId,
        customerNumber = customerNumber
      ).map { customer =>
        Some(
          sessionResolvedToken.copy(
            customerNumber = customer.map(_.number)
          )
        )
      }
    }.getOrElse(Future.successful(None))
  }

  private[this] def getCustomer(
    requestId: String,
    organizationId: String,
    customerNumber: String
  )(implicit ec: ExecutionContext): Future[Option[Customer]] = {
    customerClient.customers.getByNumber(
      organization = organizationId,
      number = customerNumber,
      requestHeaders = requestHeadersUtil.organizationAsSystemUser(
        organizationId = organizationId,
        requestId = requestId
      )
    ).map { customer =>
      Some(customer)
    }.recoverWith {
      case io.flow.customer.v0.errors.UnitResponse(_) => Future.successful(None)
      case ex: Throwable => {
        log(requestId).
          withKeyValue("customer_number", customerNumber).
          error("Error communicating with customer service", ex)
        Future.failed(ex)
      }
    }
  }

}
