const Kotlin = require('./kotlin/parseknife')

const parseknifeKt = Kotlin.dev.nickmatt.parseknife
const parseknife = new parseknifeKt.ParseKnifeJS()

const _r = parseknife.r
const r = parseknife.r = (...args) => _r.invoke(args)
for (const key in _r.constructor.prototype)
  r[key] = _r[key].bind(_r)
r.and = (...args) => _r.or(args)
r.or = (...args) => _r.or(args)

module.exports = {
  Kotlin,
  parseknife
}
