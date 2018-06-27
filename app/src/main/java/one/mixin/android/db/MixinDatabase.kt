package one.mixin.android.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import one.mixin.android.vo.Address
import one.mixin.android.vo.App
import one.mixin.android.vo.Asset
import one.mixin.android.vo.Conversation
import one.mixin.android.vo.FloodMessage
import one.mixin.android.vo.Hyperlink
import one.mixin.android.vo.Message
import one.mixin.android.vo.MessageHistory
import one.mixin.android.vo.Offset
import one.mixin.android.vo.Participant
import one.mixin.android.vo.ResendMessage
import one.mixin.android.vo.SentSenderKey
import one.mixin.android.vo.Snapshot
import one.mixin.android.vo.Sticker
import one.mixin.android.vo.StickerAlbum
import one.mixin.android.vo.StickerRelationship
import one.mixin.android.vo.User

@Database(entities = [
    (User::class),
    (Conversation::class),
    (Message::class),
    (Participant::class),
    (Offset::class),
    (Asset::class),
    (Snapshot::class),
    (MessageHistory::class),
    (SentSenderKey::class),
    (Sticker::class),
    (StickerAlbum::class),
    (App::class),
    (Hyperlink::class),
    (FloodMessage::class),
    (Address::class),
    (ResendMessage::class),
    (StickerRelationship::class)], version = 15)
abstract class MixinDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
    abstract fun participantDao(): ParticipantDao
    abstract fun offsetDao(): OffsetDao
    abstract fun assetDao(): AssetDao
    abstract fun snapshotDao(): SnapshotDao
    abstract fun messageHistoryDao(): MessageHistoryDao
    abstract fun sentSenderKeyDao(): SentSenderKeyDao
    abstract fun stickerDao(): StickerDao
    abstract fun stickerAlbumDao(): StickerAlbumDao
    abstract fun appDao(): AppDao
    abstract fun hyperlinkDao(): HyperlinkDao
    abstract fun floodMessageDao(): FloodMessageDao
    abstract fun addressDao(): AddressDao
    abstract fun resendMessageDao(): ResendMessageDao
    abstract fun stickerRelationshipDao(): StickerRelationshipDao

    companion object {
        private var INSTANCE: MixinDatabase? = null

        private val lock = Any()

        private val MIGRATION_10_11: Migration = object : Migration(10, 11) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN chain_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN shared_user_id TEXT")
            }
        }

        private val MIGRATION_11_12: Migration = object : Migration(11, 12) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN change_usd TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_btc TEXT NOT NULL DEFAULT ''")
                database.execSQL("CREATE TABLE resend_messages(message_id TEXT NOT NULL, user_id TEXT NOT NULL, " +
                    "status INTEGER NOT NULL, created_at TEXT NOT NULL, PRIMARY KEY(message_id, user_id))")
            }
        }

        private val MIGRATION_10_12: Migration = object : Migration(10, 12) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN chain_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN shared_user_id TEXT")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_usd TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_btc TEXT NOT NULL DEFAULT ''")
                database.execSQL("CREATE TABLE resend_messages(message_id TEXT NOT NULL, user_id TEXT NOT NULL, " +
                    "status INTEGER NOT NULL, created_at TEXT NOT NULL, PRIMARY KEY (message_id, user_id))")
            }
        }

        private val MIGRATION_12_13: Migration = object : Migration(12, 13) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
            }
        }

        private val MIGRATION_10_13: Migration = object : Migration(10, 13) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN chain_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN shared_user_id TEXT")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_usd TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_btc TEXT NOT NULL DEFAULT ''")
                database.execSQL("CREATE TABLE resend_messages(message_id TEXT NOT NULL, user_id TEXT NOT NULL, " +
                    "status INTEGER NOT NULL, created_at TEXT NOT NULL, PRIMARY KEY(message_id, user_id))")
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
            }
        }

        private val MIGRATION_11_13: Migration = object : Migration(11, 13) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN change_usd TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_btc TEXT NOT NULL DEFAULT ''")
                database.execSQL("CREATE TABLE resend_messages(message_id TEXT NOT NULL, user_id TEXT NOT NULL, " +
                    "status INTEGER NOT NULL, created_at TEXT NOT NULL, PRIMARY KEY(message_id, user_id))")
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
            }
        }

        private val MIGRATION_10_14: Migration = object : Migration(10, 14) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN chain_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN shared_user_id TEXT")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_usd TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_btc TEXT NOT NULL DEFAULT ''")
                database.execSQL("CREATE TABLE resend_messages(message_id TEXT NOT NULL, user_id TEXT NOT NULL, " +
                    "status INTEGER NOT NULL, created_at TEXT NOT NULL, PRIMARY KEY(message_id, user_id))")
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_waveform BLOB")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_message_id TEXT")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_content TEXT")
                database.execSQL("CREATE INDEX index_messages_user_id ON messages(user_id)")
            }
        }

        private val MIGRATION_11_14: Migration = object : Migration(11, 14) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN change_usd TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_btc TEXT NOT NULL DEFAULT ''")
                database.execSQL("CREATE TABLE resend_messages(message_id TEXT NOT NULL, user_id TEXT NOT NULL, " +
                    "status INTEGER NOT NULL, created_at TEXT NOT NULL, PRIMARY KEY(message_id, user_id))")
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_waveform BLOB")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_message_id TEXT")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_content TEXT")
                database.execSQL("CREATE INDEX index_messages_user_id ON messages(user_id)")
            }
        }

        private val MIGRATION_12_14: Migration = object : Migration(12, 14) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_waveform BLOB")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_message_id TEXT")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_content TEXT")
                database.execSQL("CREATE INDEX index_messages_user_id ON messages(user_id)")
            }
        }

        private val MIGRATION_13_14: Migration = object : Migration(13, 14) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE messages ADD COLUMN media_waveform BLOB")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_message_id TEXT")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_content TEXT")
                database.execSQL("CREATE INDEX index_messages_user_id ON messages(user_id)")
            }
        }

        private val MIGRATION_10_15: Migration = object : Migration(10, 15) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN chain_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN shared_user_id TEXT")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_usd TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_btc TEXT NOT NULL DEFAULT ''")
                database.execSQL("CREATE TABLE resend_messages(message_id TEXT NOT NULL, user_id TEXT NOT NULL, " +
                    "status INTEGER NOT NULL, created_at TEXT NOT NULL, PRIMARY KEY(message_id, user_id))")
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_waveform BLOB")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_message_id TEXT")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_content TEXT")
                database.execSQL("CREATE INDEX index_messages_user_id ON messages(user_id)")
                database.execSQL("CREATE TABLE new_stickers(sticker_id TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, asset_url TEXT NOT NULL," +
                    " asset_type TEXT NOT NULL, asset_width INTEGER NOT NULL, asset_height INTEGER NOT NULL, created_at TEXT NOT NULL DEFAULT ''," +
                    " last_use_at TEXT)")
                database.execSQL("DROP TABLE stickers")
                database.execSQL("ALTER TABLE new_stickers RENAME TO stickers")
                database.execSQL("CREATE TABLE sticker_relationships(sticker_id TEXT NOT NULL, album_id TEXT NOT NULL, PRIMARY KEY(sticker_id, album_id))")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN user_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN category TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN description TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN confirmations INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE addresses ADD COLUMN reserve TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE addresses ADD COLUMN fee TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE snapshots ADD COLUMN opponent_id TEXT")
                database.execSQL("UPDATE snapshots SET opponent_id = counter_user_id WHERE counter_user_id IS NOT NULL")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_created_at'")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_conversation_id'")
                database.execSQL("CREATE INDEX IF NOT EXISTS 'index_messages_conversation_id_created_at' ON messages ('conversation_id', 'created_at')")
            }
        }

        private val MIGRATION_11_15: Migration = object : Migration(11, 15) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE assets ADD COLUMN change_usd TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN change_btc TEXT NOT NULL DEFAULT ''")
                database.execSQL("CREATE TABLE resend_messages(message_id TEXT NOT NULL, user_id TEXT NOT NULL, " +
                    "status INTEGER NOT NULL, created_at TEXT NOT NULL, PRIMARY KEY(message_id, user_id))")
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_waveform BLOB")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_message_id TEXT")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_content TEXT")
                database.execSQL("CREATE INDEX index_messages_user_id ON messages(user_id)")
                database.execSQL("CREATE TABLE new_stickers(sticker_id TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, asset_url TEXT NOT NULL," +
                    " asset_type TEXT NOT NULL, asset_width INTEGER NOT NULL, asset_height INTEGER NOT NULL, created_at TEXT NOT NULL DEFAULT ''," +
                    " last_use_at TEXT)")
                database.execSQL("DROP TABLE stickers")
                database.execSQL("ALTER TABLE new_stickers RENAME TO stickers")
                database.execSQL("CREATE TABLE sticker_relationships(sticker_id TEXT NOT NULL, album_id TEXT NOT NULL, PRIMARY KEY(sticker_id, album_id))")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN user_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN category TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN description TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN confirmations INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE addresses ADD COLUMN reserve TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE addresses ADD COLUMN fee TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE snapshots ADD COLUMN opponent_id TEXT")
                database.execSQL("UPDATE snapshots SET opponent_id = counter_user_id WHERE counter_user_id IS NOT NULL")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_created_at'")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_conversation_id'")
                database.execSQL("CREATE INDEX IF NOT EXISTS 'index_messages_conversation_id_created_at' ON messages ('conversation_id', 'created_at')")
            }
        }

        private val MIGRATION_12_15: Migration = object : Migration(12, 15) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE apps ADD COLUMN creator_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_mime_type TEXT")
                database.execSQL("UPDATE messages SET media_mime_type = media_mine_type WHERE media_mine_type IS NOT NULL")
                database.execSQL("ALTER TABLE messages ADD COLUMN media_waveform BLOB")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_message_id TEXT")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_content TEXT")
                database.execSQL("CREATE INDEX index_messages_user_id ON messages(user_id)")
                database.execSQL("CREATE TABLE new_stickers(sticker_id TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, asset_url TEXT NOT NULL," +
                    " asset_type TEXT NOT NULL, asset_width INTEGER NOT NULL, asset_height INTEGER NOT NULL, created_at TEXT NOT NULL DEFAULT ''," +
                    " last_use_at TEXT)")
                database.execSQL("DROP TABLE stickers")
                database.execSQL("ALTER TABLE new_stickers RENAME TO stickers")
                database.execSQL("CREATE TABLE sticker_relationships(sticker_id TEXT NOT NULL, album_id TEXT NOT NULL, PRIMARY KEY(sticker_id, album_id))")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN user_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN category TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN description TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN confirmations INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE addresses ADD COLUMN reserve TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE addresses ADD COLUMN fee TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE snapshots ADD COLUMN opponent_id TEXT")
                database.execSQL("UPDATE snapshots SET opponent_id = counter_user_id WHERE counter_user_id IS NOT NULL")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_created_at'")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_conversation_id'")
                database.execSQL("CREATE INDEX IF NOT EXISTS 'index_messages_conversation_id_created_at' ON messages ('conversation_id', 'created_at')")
            }
        }

        private val MIGRATION_13_15: Migration = object : Migration(13, 15) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE messages ADD COLUMN media_waveform BLOB")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_message_id TEXT")
                database.execSQL("ALTER TABLE messages ADD COLUMN quote_content TEXT")
                database.execSQL("CREATE INDEX index_messages_user_id ON messages(user_id)")
                database.execSQL("CREATE TABLE new_stickers(sticker_id TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, asset_url TEXT NOT NULL," +
                    " asset_type TEXT NOT NULL, asset_width INTEGER NOT NULL, asset_height INTEGER NOT NULL, created_at TEXT NOT NULL DEFAULT ''," +
                    " last_use_at TEXT)")
                database.execSQL("DROP TABLE stickers")
                database.execSQL("ALTER TABLE new_stickers RENAME TO stickers")
                database.execSQL("CREATE TABLE sticker_relationships(sticker_id TEXT NOT NULL, album_id TEXT NOT NULL, PRIMARY KEY(sticker_id, album_id))")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN user_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN category TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN description TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN confirmations INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE addresses ADD COLUMN reserve TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE addresses ADD COLUMN fee TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE snapshots ADD COLUMN opponent_id TEXT")
                database.execSQL("UPDATE snapshots SET opponent_id = counter_user_id WHERE counter_user_id IS NOT NULL")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_created_at'")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_conversation_id'")
                database.execSQL("CREATE INDEX IF NOT EXISTS 'index_messages_conversation_id_created_at' ON messages ('conversation_id', 'created_at')")
            }
        }

        private val MIGRATION_14_15: Migration = object : Migration(14, 15) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE new_stickers(sticker_id TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, asset_url TEXT NOT NULL," +
                    " asset_type TEXT NOT NULL, asset_width INTEGER NOT NULL, asset_height INTEGER NOT NULL, created_at TEXT NOT NULL DEFAULT ''," +
                    " last_use_at TEXT)")
                database.execSQL("DROP TABLE stickers")
                database.execSQL("ALTER TABLE new_stickers RENAME TO stickers")
                database.execSQL("CREATE TABLE sticker_relationships(sticker_id TEXT NOT NULL, album_id TEXT NOT NULL, PRIMARY KEY(sticker_id, album_id))")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN user_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN category TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE sticker_albums ADD COLUMN description TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE assets ADD COLUMN confirmations INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE addresses ADD COLUMN reserve TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE addresses ADD COLUMN fee TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE snapshots ADD COLUMN opponent_id TEXT")
                database.execSQL("UPDATE snapshots SET opponent_id = counter_user_id WHERE counter_user_id IS NOT NULL")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_created_at'")
                database.execSQL("DROP INDEX IF EXISTS 'index_messages_conversation_id'")
                database.execSQL("CREATE INDEX IF NOT EXISTS 'index_messages_conversation_id_created_at' ON messages ('conversation_id', 'created_at')")
            }
        }

        fun getDatabase(context: Context): MixinDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                        MixinDatabase::class.java, "mixin.db")
                        .addMigrations(MIGRATION_10_11, MIGRATION_11_12, MIGRATION_10_12, MIGRATION_12_13,
                            MIGRATION_10_13, MIGRATION_11_13, MIGRATION_10_14, MIGRATION_11_14, MIGRATION_12_14, MIGRATION_13_14,
                            MIGRATION_10_15, MIGRATION_11_15, MIGRATION_12_15, MIGRATION_13_15, MIGRATION_14_15)
                        .addCallback(CALLBACK)
                        .build()
                }
                return INSTANCE as MixinDatabase
            }
        }

        private val CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("CREATE TRIGGER conversation_last_message_update AFTER INSERT ON messages BEGIN UPDATE conversations SET last_message_id = new.id WHERE conversation_id = new.conversation_id; END")
                db.execSQL("CREATE TRIGGER conversation_last_message_delete AFTER DELETE ON messages BEGIN UPDATE conversations SET last_message_id = (select id from messages where conversation_id = old.conversation_id order by created_at DESC limit 1) WHERE conversation_id = old.conversation_id; END")
                db.execSQL("CREATE TRIGGER conversation_unseen_message_count_insert AFTER INSERT ON messages BEGIN UPDATE conversations SET unseen_message_count = (SELECT count(m.id) FROM messages m, users u WHERE m.user_id = u.user_id AND u.relationship != 'ME' AND m.status = 'DELIVERED' AND conversation_id = new.conversation_id) where conversation_id = new.conversation_id; END")
                db.execSQL("CREATE TRIGGER conversation_unseen_message_count_update AFTER UPDATE ON messages BEGIN UPDATE conversations SET unseen_message_count = (SELECT count(m.id) FROM messages m, users u WHERE m.user_id = u.user_id AND u.relationship != 'ME' AND m.status = 'DELIVERED' AND conversation_id = old.conversation_id) where conversation_id = old.conversation_id; END")
            }
        }
    }
}
