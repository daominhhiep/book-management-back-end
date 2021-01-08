package bookManagement.port.primary.webService.restAdapter.common

import javax.inject.Inject
import play.api.http.DefaultHttpFilters
import play.filters.cors.CORSFilter

class HttpFilter @Inject() (corsFilter: CORSFilter) extends DefaultHttpFilters(corsFilter)
