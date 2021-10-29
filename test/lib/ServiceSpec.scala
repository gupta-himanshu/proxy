package lib

import helpers.BasePlaySpec

class ServiceSpec extends BasePlaySpec {

  "organization" in {
    Route(Method.Get, "/foo").organization("/foo") must be(None)
    Route(Method.Get, "/users").organization("/foo") must be(None)
    Route(Method.Get, "/organization").organization("/foo") must be(None)
    Route(Method.Get, "/organization/catalog").organization("/foo") must be(None)
    Route(Method.Get, "/internal/currency/rates").organization("/internal/currency/rates") must be(Some("flow"))
    Route(Method.Get, "/foo/:id").organization("/foo") must be(None)
    Route(Method.Get, "/users/:id").organization("/foo") must be(None)

    Route(Method.Get, "/:organization").organization("/flow") must be(Some("flow"))
    Route(Method.Get, "/:organization/catalog").organization("/flow/catalog") must be(Some("flow"))
    Route(Method.Get, "/:organization/currency/rates").organization("/test/currency/rates") must be(Some("test"))
    Route(Method.Get, "/organization/:id").organization("/foo") must be(None)
    Route(Method.Get, "/organization/catalog/:id").organization("/foo") must be(None)
    Route(Method.Get, "/:organization/:id").organization("/flow/5") must be(Some("flow"))
    Route(Method.Get, "/:organization/catalog/:id").organization("/flow/catalog/5") must be(Some("flow"))

    Route(Method.Get, "/channels").organization("/channels/foo") must be(None)
    Route(Method.Post, "/channels").organization("/channels/foo") must be(None)
    Route(Method.Get, "/channels/catalog").organization("/channels/foo") must be(None)
    Route(Method.Get, "/channels/:channel").organization("/channels/flow") must be(None)
    Route(Method.Get, "/channels/:channel/catalog").organization("/channels/flow/catalog") must be(None)
    Route(Method.Get, "/channels/:channel/labels").organization("/channels/ql/labels") must be(None)
    Route(Method.Get, "/channels/foo/:id").organization("/channels/foo") must be(None)
    Route(Method.Get, "/channels/channel/:id").organization("/channels/foo") must be(None)
    Route(Method.Get, "/channels/channel/catalog/:id").organization("/channels/foo") must be(None)
    Route(Method.Get, "/channels/:channel/:id").organization("/channels/flow/5") must be(None)
    Route(Method.Get, "/channels/:channel/catalog/:id").organization("/channels/flow/catalog/5") must be(None)

    Route(Method.Get, "/partners").organization("/partners/foo") must be(None)
    Route(Method.Post, "/partners").organization("/partners/foo") must be(None)
    Route(Method.Get, "/partners/catalog").organization("/partners/foo") must be(None)
    Route(Method.Get, "/partners/:partner").organization("/partners/flow") must be(None)
    Route(Method.Get, "/partners/:partner/catalog").organization("/partners/flow/catalog") must be(None)
    Route(Method.Get, "/partners/:partner/labels").organization("/partners/ql/labels") must be(None)
    Route(Method.Get, "/partners/foo/:id").organization("/partners/foo") must be(None)
    Route(Method.Get, "/partners/partner/:id").organization("/partners/foo") must be(None)
    Route(Method.Get, "/partners/partner/catalog/:id").organization("/partners/foo") must be(None)
    Route(Method.Get, "/partners/:partner/:id").organization("/partners/flow/5") must be(None)
    Route(Method.Get, "/partners/:partner/catalog/:id").organization("/partners/flow/catalog/5") must be(None)
  }

  "channel" in {
    Route(Method.Get, "/foo").channel("/foo") must be(None)
    Route(Method.Get, "/users").channel("/foo") must be(None)
    Route(Method.Get, "/organization").channel("/foo") must be(None)
    Route(Method.Get, "/organization/catalog").channel("/foo") must be(None)
    Route(Method.Get, "/internal/currency/rates").channel("/internal/currency/rates") must be(None)
    Route(Method.Get, "/foo/:id").channel("/foo") must be(None)
    Route(Method.Get, "/users/:id").channel("/foo") must be(None)

    Route(Method.Get, "/:organization").channel("/flow") must be(None)
    Route(Method.Get, "/:organization/catalog").channel("/flow/catalog") must be(None)
    Route(Method.Get, "/:organization/currency/rates").channel("/test/currency/rates") must be(None)
    Route(Method.Get, "/organization/:id").channel("/foo") must be(None)
    Route(Method.Get, "/organization/catalog/:id").channel("/foo") must be(None)
    Route(Method.Get, "/:organization/:id").channel("/flow/5") must be(None)
    Route(Method.Get, "/:organization/catalog/:id").channel("/flow/catalog/5") must be(None)

    Route(Method.Get, "/channels").channel("/channels/foo") must be(None)
    Route(Method.Post, "/channels").channel("/channels/foo") must be(None)
    Route(Method.Get, "/channels/catalog").channel("/channels/foo") must be(None)
    Route(Method.Get, "/channels/:channel").channel("/channels/flow") must be(Some("flow"))
    Route(Method.Get, "/channels/:channel/catalog").channel("/channels/flow/catalog") must be(Some("flow"))
    Route(Method.Get, "/channels/:channel/labels").channel("/channels/ql/labels") must be(Some("ql"))
    Route(Method.Get, "/channels/foo/:id").channel("/channels/foo") must be(None)
    Route(Method.Get, "/channels/channel/:id").channel("/channels/foo") must be(None)
    Route(Method.Get, "/channels/channel/catalog/:id").channel("/channels/foo") must be(None)
    Route(Method.Get, "/channels/:channel/:id").channel("/channels/flow/5") must be(Some("flow"))
    Route(Method.Get, "/channels/:channel/catalog/:id").channel("/channels/flow/catalog/5") must be(Some("flow"))

    Route(Method.Get, "/partners").channel("/partners/foo") must be(None)
    Route(Method.Post, "/partners").channel("/partners/foo") must be(None)
    Route(Method.Get, "/partners/catalog").channel("/partners/foo") must be(None)
    Route(Method.Get, "/partners/:partner").channel("/partners/flow") must be(None)
    Route(Method.Get, "/partners/:partner/catalog").channel("/partners/flow/catalog") must be(None)
    Route(Method.Get, "/partners/:partner/labels").channel("/partners/ql/labels") must be(None)
    Route(Method.Get, "/partners/foo/:id").channel("/partners/foo") must be(None)
    Route(Method.Get, "/partners/partner/:id").channel("/partners/foo") must be(None)
    Route(Method.Get, "/partners/partner/catalog/:id").channel("/partners/foo") must be(None)
    Route(Method.Get, "/partners/:partner/:id").channel("/partners/flow/5") must be(None)
    Route(Method.Get, "/partners/:partner/catalog/:id").channel("/partners/flow/catalog/5") must be(None)
  }

  "partner" in {
    Route(Method.Get, "/foo").partner("/partners/foo") must be(None)
    Route(Method.Get, "/users").partner("/partners/foo") must be(None)
    Route(Method.Get, "/organization").partner("/foo") must be(None)
    Route(Method.Get, "/organization/catalog").partner("/foo") must be(None)
    Route(Method.Get, "/internal/currency/rates").partner("/internal/currency/rates") must be(None)
    Route(Method.Get, "/foo/:id").partner("/foo") must be(None)
    Route(Method.Get, "/users/:id").partner("/partners/foo") must be(None)

    Route(Method.Get, "/:organization").partner("/flow") must be(None)
    Route(Method.Get, "/:organization/catalog").partner("/flow/catalog") must be(None)
    Route(Method.Get, "/:organization/currency/rates").partner("/test/currency/rates") must be(None)
    Route(Method.Get, "/organization/:id").partner("/foo") must be(None)
    Route(Method.Get, "/organization/catalog/:id").partner("/foo") must be(None)
    Route(Method.Get, "/:organization/:id").partner("/flow/5") must be(None)
    Route(Method.Get, "/:organization/catalog/:id").partner("/flow/catalog/5") must be(None)

    Route(Method.Get, "/channels").partner("/channels/foo") must be(None)
    Route(Method.Post, "/channels").partner("/channels/foo") must be(None)
    Route(Method.Get, "/channels/catalog").partner("/channels/foo") must be(None)
    Route(Method.Get, "/channels/:channel").partner("/channels/flow") must be(None)
    Route(Method.Get, "/channels/:channel/catalog").partner("/channels/flow/catalog") must be(None)
    Route(Method.Get, "/channels/:channel/labels").partner("/channels/ql/labels") must be(None)
    Route(Method.Get, "/channels/foo/:id").partner("/channels/foo") must be(None)
    Route(Method.Get, "/channels/channel/:id").partner("/channels/foo") must be(None)
    Route(Method.Get, "/channels/channel/catalog/:id").partner("/channels/foo") must be(None)
    Route(Method.Get, "/channels/:channel/:id").partner("/channels/flow/5") must be(None)
    Route(Method.Get, "/channels/:channel/catalog/:id").partner("/channels/flow/catalog/5") must be(None)

    Route(Method.Get, "/partners").partner("/partners/foo") must be(None)
    Route(Method.Post, "/partners").partner("/partners/foo") must be(None)
    Route(Method.Get, "/partners/catalog").partner("/partners/foo") must be(None)
    Route(Method.Get, "/partners/:partner").partner("/partners/flow") must be(Some("flow"))
    Route(Method.Get, "/partners/:partner/catalog").partner("/partners/flow/catalog") must be(Some("flow"))
    Route(Method.Get, "/partners/:partner/labels").partner("/partners/ql/labels") must be(Some("ql"))
    Route(Method.Get, "/partners/foo/:id").partner("/partners/foo") must be(None)
    Route(Method.Get, "/partners/partner/:id").partner("/partners/foo") must be(None)
    Route(Method.Get, "/partners/partner/catalog/:id").partner("/partners/foo") must be(None)
    Route(Method.Get, "/partners/:partner/:id").partner("/partners/flow/5") must be(Some("flow"))
    Route(Method.Get, "/partners/:partner/catalog/:id").partner("/partners/flow/catalog/5") must be(Some("flow"))
  }

}
