/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.10.97
 * apibuilder 0.15.33 app.apibuilder.io/flow/channel/latest/play_2_8_mock_client
 */
package io.flow.channel.v0.mock {

  trait Client extends io.flow.channel.v0.interfaces.Client {

    val baseUrl: String = "http://mock.localhost"

    override def channels: io.flow.channel.v0.Channels = MockChannelsImpl
    override def channelAuthorizations: io.flow.channel.v0.ChannelAuthorizations = MockChannelAuthorizationsImpl
    override def channelOrganizations: io.flow.channel.v0.ChannelOrganizations = MockChannelOrganizationsImpl

  }

  object MockChannelsImpl extends MockChannels

  trait MockChannels extends io.flow.channel.v0.Channels {

    /**
     * Returns the list of channels
     */
    def get(
      id: _root_.scala.Option[Seq[String]] = None,
      limit: Long = 25L,
      offset: Long = 0L,
      sort: String = "name",
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[Seq[io.flow.channel.v0.models.Channel]] = scala.concurrent.Future.successful {
      Nil
    }

    /**
     * Returns a channel by id
     */
    def getById(
      id: String,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.flow.channel.v0.models.Channel] = scala.concurrent.Future.successful {
      io.flow.channel.v0.mock.Factories.makeChannel()
    }

    def post(
      channelForm: io.flow.channel.v0.models.ChannelForm,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.flow.channel.v0.models.Channel] = scala.concurrent.Future.successful {
      io.flow.channel.v0.mock.Factories.makeChannel()
    }

    /**
     * Upserts the channel data, channel is identified by id
     */
    def putById(
      id: String,
      channelForm: io.flow.channel.v0.models.ChannelForm,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.flow.channel.v0.models.Channel] = scala.concurrent.Future.successful {
      io.flow.channel.v0.mock.Factories.makeChannel()
    }

    /**
     * Deletes a channel specified by id
     */
    def deleteById(
      id: String,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[Unit] = scala.concurrent.Future.successful {
      // unit type
    }

    /**
     * Creates a token for an organization in the channel identified by id
     */
    def postOrganizationsAndTokensByIdAndOrganizationId(
      id: String,
      organizationId: String,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.flow.token.v0.models.OrganizationToken] = scala.concurrent.Future.successful {
      io.flow.token.v0.mock.Factories.makeOrganizationToken()
    }

    /**
     * Returns the list of channel currencies
     */
    def getCurrenciesByChannelId(
      channelId: String,
      id: _root_.scala.Option[Seq[String]] = None,
      currency: _root_.scala.Option[Seq[String]] = None,
      limit: Long = 25L,
      offset: Long = 0L,
      sort: String = "-created_at",
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[Seq[io.flow.channel.v0.models.ChannelCurrency]] = scala.concurrent.Future.successful {
      Nil
    }

    def putCurrenciesByChannelId(
      channelId: String,
      channelCurrencyForm: io.flow.channel.v0.models.ChannelCurrencyForm,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.flow.channel.v0.models.ChannelCurrency] = scala.concurrent.Future.successful {
      io.flow.channel.v0.mock.Factories.makeChannelCurrency()
    }

  }

  object MockChannelAuthorizationsImpl extends MockChannelAuthorizations

  trait MockChannelAuthorizations extends io.flow.channel.v0.ChannelAuthorizations {

    def post(
      channelAuthorizationForm: io.flow.channel.v0.models.ChannelAuthorizationForm,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.flow.channel.v0.models.ChannelAuthorization] = scala.concurrent.Future.successful {
      io.flow.channel.v0.mock.Factories.makeChannelAuthorization()
    }

  }

  object MockChannelOrganizationsImpl extends MockChannelOrganizations

  trait MockChannelOrganizations extends io.flow.channel.v0.ChannelOrganizations {

    /**
     * Returns the list of organizations in a channel, channel is identified by id
     */
    def get(
      channelId: String,
      id: _root_.scala.Option[Seq[String]] = None,
      key: _root_.scala.Option[Seq[String]] = None,
      limit: Long = 25L,
      offset: Long = 0L,
      sort: String = "name",
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[Seq[io.flow.channel.v0.models.ChannelOrganization]] = scala.concurrent.Future.successful {
      Nil
    }

    def getByKey(
      channelId: String,
      key: String,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.flow.channel.v0.models.ChannelOrganization] = scala.concurrent.Future.successful {
      io.flow.channel.v0.mock.Factories.makeChannelOrganization()
    }

    def putByKey(
      channelId: String,
      key: String,
      channelOrganizationForm: io.flow.channel.v0.models.ChannelOrganizationForm,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.flow.channel.v0.models.ChannelOrganization] = scala.concurrent.Future.successful {
      io.flow.channel.v0.mock.Factories.makeChannelOrganization()
    }

    def deleteByKey(
      channelId: String,
      key: String,
      requestHeaders: Seq[(String, String)] = Nil
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[Unit] = scala.concurrent.Future.successful {
      // unit type
    }

  }

  object Factories {

    def randomString(length: Int = 24): String = {
      _root_.scala.util.Random.alphanumeric.take(length).mkString
    }

    def makeChannelCurrencyCapability(): io.flow.channel.v0.models.ChannelCurrencyCapability = io.flow.channel.v0.models.ChannelCurrencyCapability.PaymentAuthorizations

    def makeChannel(): io.flow.channel.v0.models.Channel = io.flow.channel.v0.models.Channel(
      id = Factories.randomString(24),
      name = Factories.randomString(24),
      environment = io.flow.common.v0.mock.Factories.makeEnvironment(),
      organizationIdPrefix = None
    )

    def makeChannelAuthorization(): io.flow.channel.v0.models.ChannelAuthorization = io.flow.channel.v0.models.ChannelAuthorization(
      placeholder = None
    )

    def makeChannelAuthorizationForm(): io.flow.channel.v0.models.ChannelAuthorizationForm = io.flow.channel.v0.models.ChannelAuthorizationForm(
      channelId = Factories.randomString(24)
    )

    def makeChannelCurrency(): io.flow.channel.v0.models.ChannelCurrency = io.flow.channel.v0.models.ChannelCurrency(
      id = Factories.randomString(24),
      currency = Factories.randomString(24),
      channel = io.flow.common.v0.mock.Factories.makeChannelReference(),
      capabilities = Nil
    )

    def makeChannelCurrencyForm(): io.flow.channel.v0.models.ChannelCurrencyForm = io.flow.channel.v0.models.ChannelCurrencyForm(
      currency = Factories.randomString(24),
      capabilities = Nil
    )

    def makeChannelForm(): io.flow.channel.v0.models.ChannelForm = io.flow.channel.v0.models.ChannelForm(
      name = Factories.randomString(24),
      environment = io.flow.common.v0.mock.Factories.makeEnvironment(),
      organizationIdPrefix = None
    )

    def makeChannelOrganization(): io.flow.channel.v0.models.ChannelOrganization = io.flow.channel.v0.models.ChannelOrganization(
      id = Factories.randomString(24),
      organization = io.flow.common.v0.mock.Factories.makeOrganizationReference(),
      key = Factories.randomString(24),
      channel = io.flow.common.v0.mock.Factories.makeChannelReference(),
      name = Factories.randomString(24),
      slug = None,
      defaults = io.flow.common.v0.mock.Factories.makeOrganizationDefaults(),
      attributes = None
    )

    def makeChannelOrganizationForm(): io.flow.channel.v0.models.ChannelOrganizationForm = io.flow.channel.v0.models.ChannelOrganizationForm(
      name = Factories.randomString(24),
      slug = None,
      defaults = io.flow.common.v0.mock.Factories.makeOrganizationDefaults(),
      attributes = None
    )

  }

}